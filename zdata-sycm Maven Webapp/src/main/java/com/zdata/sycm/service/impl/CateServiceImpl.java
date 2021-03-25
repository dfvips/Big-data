package com.zdata.sycm.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zdata.sycm.dao.CateMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.CateService;
import com.zdata.sycm.service.RedisService;

@Service("cateService")
public class CateServiceImpl implements CateService {

	@Autowired
	private CateMapper cateMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public RetResult<?> findTree() throws Exception {
		Cate cate = new Cate();
		if(redisService.hasKey("sycm_cate")){
			String sycm_cate = (String) redisService.get("sycm_cate");
			cate = (Cate) JSONObject.toJavaObject(JSONObject.parseObject(sycm_cate), Cate.class);
		}else{
			List<Cate> rood = cateMapper.findByPiId(-1);
			if (rood!=null&&rood.size()>0) {
				cate = rood.get(0);
				List<Cate> second =  cateMapper.findByPiId(cate.getCatId());
				if(second!=null&&second.size()>0){
					for (Cate cate2 : second) {
						List<Cate> three = cateMapper.findByPiId(cate2.getCatId());
						cate2.setChildren(three);
					}
					cate.setChildren(second);
				}
			}
			String sycm_cate = JSONObject.toJSONString(cate);
			redisService.set("sycm_cate", sycm_cate);
		}
		return RetResponse.makeOKRsp(cate);
	}

}

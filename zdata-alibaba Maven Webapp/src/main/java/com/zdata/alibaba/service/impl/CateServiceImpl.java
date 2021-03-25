package com.zdata.alibaba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.alibaba.dao.CateMapper;
import com.zdata.alibaba.model.Cate;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.CateService;

@Service
public class CateServiceImpl implements CateService {

	@Autowired
	private CateMapper cateMapper;
	
	@Override
	public RetResult<?> findTree() throws Exception {
		List<Cate> rood = cateMapper.findByCatParent(-1);
		Cate cate = new Cate();
		if (rood!=null&&rood.size()>0) {
			cate = rood.get(0);
			List<Cate> second =  cateMapper.findByCatParent(cate.getCatId());
			if(second!=null&&second.size()>0){
				for (Cate cate2 : second) {
					List<Cate> three = cateMapper.findByCatParent(cate2.getCatId());
					cate2.setChildren(three);
				}
				cate.setChildren(second);
			}
		}
		return RetResponse.makeOKRsp(cate);
	}

}

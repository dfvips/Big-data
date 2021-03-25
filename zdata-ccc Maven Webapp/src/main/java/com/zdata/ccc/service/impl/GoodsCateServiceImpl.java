package com.zdata.ccc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.ccc.dao.GoodsCateMapper;
import com.zdata.ccc.model.GoodsCate;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.GoodsCateService;

@Service("goodsCateService")
public class GoodsCateServiceImpl implements GoodsCateService {

	@Autowired
	private GoodsCateMapper goodsCateMapper;
	
	@Override
	public RetResult<?> findTree() throws Exception{
		List<GoodsCate> rood = goodsCateMapper.findByPiId(0);
		GoodsCate goodsCate = new GoodsCate();
		if (rood!=null&&rood.size()>0) {
			goodsCate = rood.get(0);
			List<GoodsCate> second =  goodsCateMapper.findByPiId(goodsCate.getCatId());
			if(second!=null&&second.size()>0){
				for (GoodsCate goodsCate2 : second) {
					List<GoodsCate> three = goodsCateMapper.findByPiId(goodsCate2.getCatId());
					goodsCate2.setChildren(three);
				}
				goodsCate.setChildren(second);
			}
		}
		return RetResponse.makeOKRsp(goodsCate);
	}

	@Override
	public GoodsCate getTree() throws Exception {
		List<GoodsCate> rood = goodsCateMapper.findByPiId(0);
		GoodsCate goodsCate = new GoodsCate();
		if (rood!=null&&rood.size()>0) {
			goodsCate = rood.get(0);
			List<GoodsCate> second =  goodsCateMapper.findByPiId(goodsCate.getCatId());
			if(second!=null&&second.size()>0){
				for (GoodsCate goodsCate2 : second) {
					List<GoodsCate> three = goodsCateMapper.findByPiId(goodsCate2.getCatId());
					goodsCate2.setChildren(three);
				}
				goodsCate.setChildren(second);
			}
		}
		return goodsCate;
	}
}

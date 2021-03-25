package com.zdata.alibaba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.alibaba.dao.RateWordsMapper;
import com.zdata.alibaba.model.RateWords;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.RateWordsService;
import com.zdata.alibaba.vo.search.RateWordsSearchVo;

@Service
public class RateWordsServiceImpl implements RateWordsService {

	@Autowired
	private RateWordsMapper rateWordsMapper;
	
	@Override
	public RetResult<?> find(RateWordsSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()==97) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<RateWords> list = rateWordsMapper.find(searchVo);
		PageInfo<RateWords> info = new PageInfo<RateWords>(list);
		return RetResponse.makeOKRsp(list, (int) info.getTotal());
	}

}

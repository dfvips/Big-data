package com.zdata.alibaba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.alibaba.dao.NewWordsMapper;
import com.zdata.alibaba.model.NewWords;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.NewWordsService;
import com.zdata.alibaba.vo.search.NewWordsSearchVo;

@Service
public class NewWordsServiceImpl implements NewWordsService {

	@Autowired
	private NewWordsMapper newWordsMapper;
	
	@Override
	public RetResult<?> find(NewWordsSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()==97) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<NewWords> list = newWordsMapper.find(searchVo);
		PageInfo<NewWords> info = new PageInfo<NewWords>(list);
		return RetResponse.makeOKRsp(list, (int) info.getTotal());
	}

}

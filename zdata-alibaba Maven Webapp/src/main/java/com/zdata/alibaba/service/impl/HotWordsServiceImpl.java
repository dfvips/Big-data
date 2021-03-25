package com.zdata.alibaba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.alibaba.dao.HotWordsMapper;
import com.zdata.alibaba.model.HotWords;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.HotWordsService;
import com.zdata.alibaba.vo.search.HotWordsSearchVo;

@Service
public class HotWordsServiceImpl implements HotWordsService {

	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Override
	public RetResult<?> find(HotWordsSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()==97) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotWords> list = hotWordsMapper.find(searchVo);
		PageInfo<HotWords> info = new PageInfo<HotWords>(list);
		return RetResponse.makeOKRsp(list, (int) info.getTotal());
	}

}

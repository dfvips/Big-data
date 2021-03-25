package com.zdata.alibaba.service;

import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.vo.search.RateWordsSearchVo;

public interface RateWordsService {

	public RetResult<?> find(RateWordsSearchVo searchVo) throws Exception;
	
}

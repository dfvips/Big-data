package com.zdata.alibaba.service;

import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.vo.search.HotWordsSearchVo;

public interface HotWordsService {

	public RetResult<?> find(HotWordsSearchVo searchVo) throws Exception;
	
}

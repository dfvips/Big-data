package com.zdata.sycm.service;

import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.vo.search.SearchWordSoarSearchVo;

public interface SearchWordSoarService {

	public RetResult<?> findNew(SearchWordSoarSearchVo searchVo) throws Exception;
	
}

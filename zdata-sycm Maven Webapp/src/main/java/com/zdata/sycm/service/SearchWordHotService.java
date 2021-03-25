package com.zdata.sycm.service;

import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.vo.search.SearchWordHotSearchVo;

public interface SearchWordHotService {

	public RetResult<?> findNew(SearchWordHotSearchVo searchVo) throws Exception;
	
}

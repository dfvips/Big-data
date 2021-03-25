package com.zdata.ccc.service;

import com.zdata.ccc.result.RetResult;

public interface HotTrendService {

	public RetResult<?> findByHotRiseId(Integer hotRiseId) throws Exception;
	
}

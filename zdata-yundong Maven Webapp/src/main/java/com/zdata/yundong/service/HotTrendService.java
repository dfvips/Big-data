package com.zdata.yundong.service;

import com.zdata.yundong.result.RetResult;

public interface HotTrendService {

	public RetResult<?> findByHotRiseId(Integer hotRiseId) throws Exception;
	
}

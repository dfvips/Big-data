package com.zdata.alibaba.service;

import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.vo.search.HotRiseSearchVo;

public interface HotRiseService {

	public void updateRise(String riseDate) throws Exception;
	
	public RetResult<?> find(HotRiseSearchVo searchVo) throws Exception;
	
}

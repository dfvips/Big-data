package com.zdata.yundong.service.impl;

import org.springframework.stereotype.Service;

import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.CateService;

@Service
public class CateServiceImpl implements CateService {
	
	@Override
	public RetResult<?> find() throws Exception {
		
		return RetResponse.makeOKRsp();
	}

}

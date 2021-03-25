package com.zdata.ccc.service.impl;

import org.springframework.stereotype.Service;

import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.CateService;

@Service
public class CateServiceImpl implements CateService {
	
	@Override
	public RetResult<?> find() throws Exception {
		
		return RetResponse.makeOKRsp();
	}

}

package com.zdata.ias.service;

import com.zdata.ias.model.SysAuth;


public interface DSysAuthService {

	public SysAuth loadByUserId(String userId, String funcId);
	
}

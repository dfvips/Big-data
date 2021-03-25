package com.zdata.ias.service.impl;

import com.zdata.dao.SysAuthDao;

@Service("dSysAuthService")
public class DSysAuthServiceImpl implements DSysAuthService {

	@Resource
	private SysAuthDao sysAuthDao;
	
	@Override
	public SysAuth loadByUserId(String userId, String funcId) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper.writeValueAsString(sysAuthDao.loadByUserId(userId, funcId));
			SysAuth auth = objectMapper.readValue(jsonString, SysAuth.class);
			return auth;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

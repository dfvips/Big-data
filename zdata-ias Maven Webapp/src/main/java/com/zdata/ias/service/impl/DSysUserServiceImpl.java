package com.zdata.ias.service.impl;

import com.zdata.dao.SysUserDao;

@Service("dSysUserService")
public class DSysUserServiceImpl implements DSysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public SysUser loadByUserId(String userId) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper.writeValueAsString(sysUserDao.loadByUserId(userId));
			SysUser user = objectMapper.readValue(jsonString, SysUser.class);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

}

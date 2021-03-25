package com.zdata.ias.service;

import com.zdata.ias.model.SysUser;

public interface DSysUserService {
	
	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	public SysUser loadByUserId(String userId);
	
}

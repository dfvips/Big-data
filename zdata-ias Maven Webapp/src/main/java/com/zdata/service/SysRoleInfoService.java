package com.zdata.service;

import java.util.Map;

public interface SysRoleInfoService {

	public Map<String, Object> findByRoleId(Integer roleId,Integer page,Integer rows);
	
	public Map<String, Object> delete(Integer[] ids,String[] userIds,Integer roleId);
	
	public Map<String, Object> saveUsers(String[] users,Integer roleId);
	
}

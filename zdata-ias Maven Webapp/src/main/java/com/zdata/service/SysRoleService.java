package com.zdata.service;

import java.util.Map;

import com.zdata.model.SysRole;

public interface SysRoleService {

	public Map<String, Object> findByAll(SysRole record);
	
	public Map<String, Object> save(SysRole record);
	
	public Map<String, Object> delete(Integer id);
	
	public Map<String, Object> loadById(Integer id);
	
	/**
	 * 角色授权
	 * @param id
	 * @param funcIds
	 * @return
	 */
	public Map<String, Object> setAuth(Integer id,String[] funcIds);
	
}

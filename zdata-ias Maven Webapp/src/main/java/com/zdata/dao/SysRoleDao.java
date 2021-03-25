package com.zdata.dao;

import java.util.List;

import com.zdata.model.SysRole;

public interface SysRoleDao {

	public void add(SysRole sysRole);
	
	public void update(SysRole sysRole);
	
	public List<SysRole> findByAll(SysRole record);
	
	public int findCountByAll(SysRole record);
	
	public void delete(Integer id);
	
	public SysRole loadById(Integer id);
	
}

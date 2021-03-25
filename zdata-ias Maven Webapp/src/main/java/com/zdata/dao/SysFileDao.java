package com.zdata.dao;

import java.util.List;

import com.zdata.model.SysFile;

public interface SysFileDao {

	public void add(SysFile record);
	
	public boolean update(SysFile record);
	
	public void delete(int id);
	
	public List<SysFile> find(SysFile record);
	
	public int count(SysFile record);
	
	public void deleteByBus(String busType,int busId);
	
}

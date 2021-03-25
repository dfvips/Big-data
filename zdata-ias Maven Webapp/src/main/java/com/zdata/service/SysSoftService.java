package com.zdata.service;

import java.util.Map;

import com.zdata.model.SysSoft;

public interface SysSoftService {

	SysSoft findBySoftId(String softId);
	
	Map<String, Object> findByAll(SysSoft record);
	
	Map<String, Object> save(SysSoft record);
	
	Map<String, Object> findAll();
	
}

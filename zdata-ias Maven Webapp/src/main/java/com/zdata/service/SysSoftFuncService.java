package com.zdata.service;

import java.util.Map;

public interface SysSoftFuncService {

	/**
	 * 通过软件Id查询软件预设权限说
	 * @param softId
	 * @return
	 */
	public Map<String, Object> findBySoftId(Integer softId);
	
	/**
	 * 保存欲设权限
	 * @param softId
	 * @param funcIds
	 * @return
	 */
	public Map<String, Object> saveBySoftId(Integer softId,String[] funcIds);
}

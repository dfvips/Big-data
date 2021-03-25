package com.zdata.service;

import java.util.Map;

import com.zdata.model.SysFunc;

public interface SysFuncService {

	Map<String, Object> findByPidAndUserId(String funcId, String userId, String publicKey, HttpServletRequest request);

	Map<String, Object> findBySoftId(Integer softId);
	
	Map<String, Object> save(SysFunc sysFunc);

	Map<String, Object> deleteByFuncId(String funcId);
	
	Map<String, Object> findByFuncId(String funcId);
	
	/**
	 * 功能复用
	 * @param:        @param selectFuncId
	 * @param:        @param objectFuncId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 下午10:22:35 
	 */
	Map<String, Object> saveCopy(String selectFuncId,String objectFuncId);
}

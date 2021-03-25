package com.zdata.service;

import java.util.Map;

/**
 * 
 * @author 梦丶随心飞
 *
 */
public interface UserGrantService {

	/**
	 * 查询用户权限
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月21日 下午11:28:20 
	 */
	public Map<String, Object> findByUserId(String userId);
	
}

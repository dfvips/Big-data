package com.zdata.zjbs.service;

import java.util.Map;

public interface SysXtUserInfoService {
	/**
	 * 用户ID查询用户基本信息
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年6月5日 下午11:28:38 
	 */
	public Map<String, Object> findByUserId(String userId);
}

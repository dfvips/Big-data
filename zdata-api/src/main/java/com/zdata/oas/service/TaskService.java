package com.zdata.oas.service;

import java.util.Map;

public interface TaskService {

	/**
	 * 查询全部任务
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年6月8日 下午10:38:10 
	 */
	public Map<String, Object> findAllTask(String userId);
	
	/**
	 * 查询个人代办任务
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findMyTask(String userId);
	
	/**
	 * 共享任务
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findShareTask(String userId);
	
	/**
	 * 已办任务
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findMyTaskDone(String userId);
	
	/**
	 * 查询流程追踪
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findMyInvolved(String userId);
}

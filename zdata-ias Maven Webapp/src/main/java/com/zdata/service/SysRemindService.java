package com.zdata.service;

import java.util.Map;

import com.zdata.model.SysRemind;
import com.zdata.model.SysUser;

public interface SysRemindService {

	public Map<String,Object> findByAll(SysRemind record,SysUser curUser);
	
	/**
	 * 更新用户提示信息
	 * 1.从zjbs项目中获取用户信息记录，更新完善个人信息
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年6月6日 下午7:27:15 
	 */
	public Map<String,Object> updateByUserId(String userId);
}

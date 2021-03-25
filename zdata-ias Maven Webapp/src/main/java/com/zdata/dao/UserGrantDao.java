package com.zdata.dao;

import java.util.List;

import com.zdata.model.UserGrant;

public interface UserGrantDao {

	/**
	 * 查询用户权限
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月21日 下午11:10:23 
	 */
	public List<UserGrant> findByUserId(String userId);
	
	/**
	 * 删除用户权限
	 * @param userId
	 */
	public void deleteByUserId(String userId);
	
	/**
	 * 添加
	 * @param userGrant
	 */
	public void add(UserGrant userGrant);
}

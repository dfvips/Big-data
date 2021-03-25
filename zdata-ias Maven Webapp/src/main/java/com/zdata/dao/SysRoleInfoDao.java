package com.zdata.dao;

import java.util.List;

import com.zdata.model.SysRoleInfo;

public interface SysRoleInfoDao {

	/**
	 * 用户角色信息
	 * @param:        @param roleId
	 * @param:        @param page
	 * @param:        @param rows
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月19日 下午6:37:19 
	 */
	public List<SysRoleInfo> findByRoleId(Integer roleId,Integer page,Integer rows);
	
	/**
	 * 用户Id查询用户角色列表
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月19日 下午6:37:58 
	 */
	public List<SysRoleInfo> findByUserId(String userId);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Integer[] ids);
	
	/**
	 * 删除明细
	 * @param roleId
	 */
	public void deleteByRoleId(Integer roleId);
	
	/**
	 * 判断用户是否存在
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public boolean exitByUser(Integer roleId,String userId);
	
	/**
	 * 添加
	 * @param sysRoleInfo
	 */
	public void add(SysRoleInfo sysRoleInfo);
	
	/**
	 * 删除用户拥有角色
	 * @param:        @param userId    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月20日 下午9:33:20 
	 */
	public void deleteByUserId(String userId);
	
}

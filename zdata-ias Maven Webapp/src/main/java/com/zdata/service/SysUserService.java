package com.zdata.service;

import java.util.List;
import java.util.Map;

import com.zdata.model.PageBean;
import com.zdata.model.SysUser;


public interface SysUserService {

	/**
	 * 组织id查找用户列表
	 * <p>Title: findByOrgId</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	public List<SysUser> findByOrgId(PageBean pageBean,String search,String orgId,boolean isLike);
	
	/**
	 * 
	 * <p>Title: countByOrgId</p>  
	 * <p>Description: </p>  
	 * @param search
	 * @param orgId
	 * @return
	 */
	public int countByOrgId(String search,String orgId,boolean isLike);
	
	/**
	 * 用户Id
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月18日 下午9:33:52 
	 */
	public SysUser loadByUserId(String userId);
	
	/**
	 * 主键Id
	 * @param:        @param id
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月19日 上午10:05:46 
	 */
	public Map<String, Object> loadById(Integer id);

	/**
	 * 
	 * @param:        @param sysUser
	 * @param:        @param request
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月19日 下午12:00:03 
	 */
	public Map<String, Object> save(SysUser sysUser, HttpServletRequest request);
	
	/**
	 * 刪除用戶
	 * @param:        @param id
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月20日 下午9:30:27 
	 */
	public Map<String, Object> deleteById(Integer id);
	
	/**
	 * 电话查询
	 * @param:        @param mobtele
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 上午11:24:34
	 */
	public SysUser loadByMobtele(String mobtele);
	
}

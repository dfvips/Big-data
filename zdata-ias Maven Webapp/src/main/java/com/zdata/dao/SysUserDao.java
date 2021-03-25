package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.PageBean;
import com.zdata.model.SysUser;

/**
* sys_user
* @author 梦丶随心飞 2020-07-18
*/
public interface SysUserDao {

	int add(SysUser sysUser);

    int update(SysUser sysUser);

    int delete(int id);

    SysUser findById(int id);

    List<SysUser> findAllList(Map<String,Object> param);
    
	/**
	 * 组织id查找用户列表
	 * <p>Title: findByOrgId</p>  
	 * <p>Description: </p>  
	 * @param pageBean
	 * @param search
	 * @param orgId
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
	 * 用户Id查询
	 * @param:        @param userId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月18日 下午9:47:10 
	 */
	public SysUser loadByUserId(String userId);
	
	/**
	 * 
	 * @param:        @param softId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月21日 下午9:40:39 
	 */
	public List<SysUser> findBySoftIdOrderByUserId(String softId);
	
	/**
	 * 电话查询
	 * @param:        @param mobtele
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 上午11:25:49 
	 */
	public SysUser loadByMobtele(String mobtele);
    
}

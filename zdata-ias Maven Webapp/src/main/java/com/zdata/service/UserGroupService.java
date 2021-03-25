package com.zdata.service;

import java.util.List;

import com.zdata.model.PageBean;
import com.zdata.model.User;
import com.zdata.model.UserGroup;

/**
 * 
* <p>Title: UserGroupService</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年10月5日
 */
public interface UserGroupService {
	
	/**
	 * 
	 * <p>Title: findByOrgId</p>  
	 * <p>Description: </p>  
	 * @param pageBean
	 * @param searchUser
	 * @param orgId
	 * @return
	 */
	public List<UserGroup> findByOrgId(PageBean pageBean,User searchUser,String orgId);
	
	/**
	 * 
	 * <p>Title: countByOrgId</p>  
	 * <p>Description: </p>  
	 * @param searchUser
	 * @param orgId
	 * @return
	 */
	public int countByOrgId(User searchUser,String orgId);
	
	/**
	 * 
	 * <p>Title: add</p>  
	 * <p>Description: </p>  
	 * @param userGroup
	 */
	public void add(UserGroup userGroup);
	
	/**
	 * 
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @param rowId
	 */
	public void delete(int rowId);
	
	/**
	 * 
	 * <p>Title: existByOrgIdAndUserId</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @param userId
	 * @return
	 */
	public boolean existByOrgIdAndUserId(String orgId,String userId);
}

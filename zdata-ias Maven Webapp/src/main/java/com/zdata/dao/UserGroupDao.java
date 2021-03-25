package com.zdata.dao;

import java.util.List;

import com.zdata.model.PageBean;
import com.zdata.model.User;
import com.zdata.model.UserGroup;

public interface UserGroupDao {
	
	public List<UserGroup> findByOrgId(PageBean pageBean,User s_User,String orgId);
	
	public int countByOrgId(User s_User,String orgId);
	
	public void add(UserGroup userGroup);
	
	public void delete(int rowId);
	
	public boolean existByOrgIdAndUserId(String orgId,String userId);
	
}

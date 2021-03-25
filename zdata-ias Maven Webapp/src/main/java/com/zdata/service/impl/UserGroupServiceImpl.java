package com.zdata.service.impl;

import java.util.List;

import com.zdata.dao.UserGroupDao;
import com.zdata.model.PageBean;
import com.zdata.model.User;
import com.zdata.model.UserGroup;
import com.zdata.service.UserGroupService;

@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Resource
	private UserGroupDao userGroupDao;
	
	@Override
	public List<UserGroup> findByOrgId(PageBean pageBean, User s_User,
			String orgId) {
		return userGroupDao.findByOrgId(pageBean, s_User, orgId);
	}

	@Override
	public int countByOrgId(User s_User, String orgId) {
		return userGroupDao.countByOrgId(s_User, orgId);
	}

	@Override
	public void add(UserGroup userGroup) {
		userGroupDao.add(userGroup);
	}

	@Override
	public void delete(int rowId) {
		userGroupDao.delete(rowId);		
	}

	@Override
	public boolean existByOrgIdAndUserId(String orgId, String userId) {
		return userGroupDao.existByOrgIdAndUserId(orgId, userId);
	}

}

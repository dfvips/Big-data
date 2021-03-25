package com.zdata.dao;

import com.zdata.model.SysAuth;

public interface SysAuthDao {

	public SysAuth loadByUserId(String userId,String funcId);
	
	public void add(SysAuth sysAuth);
	
	public void update(SysAuth sysAuth);
	
	/**
	 * 用户Id和菜单查询是否存在纪录
	 * @param:        @param userId
	 * @param:        @param funcId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月10日 下午7:17:53 
	 */
	public boolean exitByUserIdAndFuncId(String userId,String funcId);
}

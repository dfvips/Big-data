package com.zdata.service;

import com.zdata.model.SysUser;

public interface LoginService {

	public void setLogin(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) throws Exception;
}

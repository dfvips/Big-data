package com.zdata.vo;

import com.zdata.model.SysAuth;

public class SysAuthVo extends SysAuth {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sysOrgs;
	
	private String sysUsers;

	public String getSysOrgs() {
		return sysOrgs;
	}

	public void setSysOrgs(String sysOrgs) {
		this.sysOrgs = sysOrgs;
	}

	public String getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(String sysUsers) {
		this.sysUsers = sysUsers;
	}
	
	

}

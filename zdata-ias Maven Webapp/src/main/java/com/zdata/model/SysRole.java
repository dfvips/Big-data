package com.zdata.model;

import java.util.List;

import com.zdata.common.BaseParam;

public class SysRole extends BaseParam{

 	private Integer id;
 	private String roleId;
 	private String roleName;
 	private Integer softId;
 	private List<SysRoleFunc> roleFuncList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getSoftId() {
		return softId;
	}
	public void setSoftId(Integer softId) {
		this.softId = softId;
	}
	public List<SysRoleFunc> getRoleFuncList() {
		return roleFuncList;
	}
	public void setRoleFuncList(List<SysRoleFunc> roleFuncList) {
		this.roleFuncList = roleFuncList;
	}
	
}

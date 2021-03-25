package com.zdata.model;

import java.util.List;

import com.zdata.common.BaseParam;

public class SysRoleInfo extends BaseParam {

	private Integer id;
	private Integer roleId;
	private String userId;
	private String userName;
	private List<UserGrant> userGrantList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<UserGrant> getUserGrantList() {
		return userGrantList;
	}
	public void setUserGrantList(List<UserGrant> userGrantList) {
		this.userGrantList = userGrantList;
	}
	public SysRoleInfo(){
		super();
	}
	public SysRoleInfo(Integer roleId,String userId){
		super();
		this.roleId=roleId;
		this.userId=userId;
	}
}

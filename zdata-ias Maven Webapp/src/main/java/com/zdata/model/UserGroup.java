package com.zdata.model;

import java.util.Date;

public class UserGroup {
	private Integer rowId;
	private String sysUserId;
	private String sysUserName;
	private String sysUserGroupId;
	private String sysUserGroupName;
	private String sysUserPosition;
	private String positionOrder;
	private Date exchangDate;
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getSysUserName() {
		return sysUserName;
	}
	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}
	public String getSysUserGroupId() {
		return sysUserGroupId;
	}
	public void setSysUserGroupId(String sysUserGroupId) {
		this.sysUserGroupId = sysUserGroupId;
	}
	public String getSysUserGroupName() {
		return sysUserGroupName;
	}
	public void setSysUserGroupName(String sysUserGroupName) {
		this.sysUserGroupName = sysUserGroupName;
	}
	public String getSysUserPosition() {
		return sysUserPosition;
	}
	public void setSysUserPosition(String sysUserPosition) {
		this.sysUserPosition = sysUserPosition;
	}
	public String getPositionOrder() {
		return positionOrder;
	}
	public void setPositionOrder(String positionOrder) {
		this.positionOrder = positionOrder;
	}
	public Date getExchangDate() {
		return exchangDate;
	}
	public void setExchangDate(Date exchangDate) {
		this.exchangDate = exchangDate;
	}
	
}

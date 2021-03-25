package com.zdata.model;

import java.io.Serializable;
import java.util.Date;

import com.zdata.common.BaseParam;

/**
 * 用户实体
 * @author 梦丶随心飞
 *
 */
public class User extends BaseParam implements Serializable{

	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private Integer rowId;
	private String sysUserId;
	private String sysUserName;
	private String sysUserPassword;
	private String sysUserGroupId;
	private String sysUserGroupName;
	private String sysSoftId;
	private String sysSoftMainf;
	private String email;
	private String mobtele;
	private String state;
	private Date exchangDate;
	private String create;
	private String createName;
	//手机验证码
	private String code;
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
	public String getSysUserPassword() {
		return sysUserPassword;
	}
	public void setSysUserPassword(String sysUserPassword) {
		this.sysUserPassword = sysUserPassword;
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
	public String getSysSoftId() {
		return sysSoftId;
	}
	public void setSysSoftId(String sysSoftId) {
		this.sysSoftId = sysSoftId;
	}
	public String getSysSoftMainf() {
		return sysSoftMainf;
	}
	public void setSysSoftMainf(String sysSoftMainf) {
		this.sysSoftMainf = sysSoftMainf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobtele() {
		return mobtele;
	}
	public void setMobtele(String mobtele) {
		this.mobtele = mobtele;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getExchangDate() {
		return exchangDate;
	}
	public void setExchangDate(Date exchangDate) {
		this.exchangDate = exchangDate;
	}
	public String getCreate() {
		return create;
	}
	public void setCreate(String create) {
		this.create = create;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}

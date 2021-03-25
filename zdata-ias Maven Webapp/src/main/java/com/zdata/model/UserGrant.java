package com.zdata.model;

/**
 * 用户权限表
 * @author 梦丶随心飞
 *
 */
public class UserGrant {

	private Integer rowId;
	
	private String userId;
	
	private String grantId;

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGrantId() {
		return grantId;
	}

	public void setGrantId(String grantId) {
		this.grantId = grantId;
	}
	
	
	public UserGrant(){
		super();
	}
	
	public UserGrant(String userId,String grantId){
		super();
		this.userId = userId;
		this.grantId = grantId;
	}
}

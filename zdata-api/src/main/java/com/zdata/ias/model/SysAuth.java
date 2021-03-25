package com.zdata.ias.model;

import java.io.Serializable;

/**
 * sys_auth实体类
 * 
 * @author 
 *
 */
public class SysAuth implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id; 
	/**用户Id*/
	private String userId; 
	/**菜单Id*/
	private String funcId; 
	/**组织Id*/
	private String orgId; 
	/**权限类型：0：本人（默认）1：本人所属部门 2：指定部门 3：全部*/
	private Integer authType; 
	
	/**可见用户Ids*/
	private String authUserIds;
	
	public SysAuth(String userId,String funcId,String orgId,Integer authType,String authUserIds){
		super();
		this.userId=userId;
		this.funcId=funcId;
		this.orgId=orgId;
		this.authType=authType;
		this.authUserIds=authUserIds;
	}
	
	/**
	 * 实例化
	 */
	public SysAuth() {
		super();
	}
	
	
	/**
	 * 获取id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 获取userId
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置userId
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 获取funcId
	 * 
	 * @return
	 */
	public String getFuncId() {
		return funcId;
	}

	/**
	 * 设置funcId
	 * 
	 * @param funcId
	 */
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	
	/**
	 * 获取orgId
	 * 
	 * @return
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 设置orgId
	 * 
	 * @param orgId
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 获取authType
	 * 
	 * @return
	 */
	public Integer getAuthType() {
		return authType;
	}

	/**
	 * 设置authType
	 * 
	 * @param authType
	 */
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	/**
	 * 获取authUserIds
	 * @return
	 */
	public String getAuthUserIds() {
		return authUserIds;
	}
	
	/**
	 * 设置authUserIds
	 * @param authUserIds
	 */
	public void setAuthUserIds(String authUserIds) {
		this.authUserIds = authUserIds;
	}
	
	@Override
	public String toString() {
		return "SysAuth [id=" + id + " , userId=" + userId + " , funcId=" + funcId + " , orgId=" + orgId + " , authType=" + authType + " , authUserIds=" + authUserIds + "  ]";
	}
	
	
}

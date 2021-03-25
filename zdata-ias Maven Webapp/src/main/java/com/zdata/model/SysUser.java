package com.zdata.model;

import java.io.Serializable;
import java.util.Date;

/**
*  sys_user
* @author 梦丶随心飞 2020-07-18
*/
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private Integer id;

    /**
    * 用户id
    */
    private String userId;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String userPassword;

    /**
    * 小组号
    */
    private String userGroupId;

    /**
    * 软件id
    */
    private String softId;

    /**
    * 主程序名称
    */
    private String softMain;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 手机号
    */
    private String mobtele;

    /**
    * 状态0新用户
    */
    private String state;

    /**
    * 修改日期
    */
    private Date exchangDate;

    /**
    * 创建者
    */
    private String createName;

    /**
    * 工作流系统密码
    */
    private String flowPassword;

    /**
    * 角色id
    */
    private Integer roleId;


    private String publicKey;
    
    private String encoded;
    
    private String code;
    
    private SysUserDy sysUserDy;
    
    public SysUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getSoftId() {
        return softId;
    }

    public void setSoftId(String softId) {
        this.softId = softId;
    }

    public String getSoftMain() {
        return softMain;
    }

    public void setSoftMain(String softMain) {
        this.softMain = softMain;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getFlowPassword() {
        return flowPassword;
    }

    public void setFlowPassword(String flowPassword) {
        this.flowPassword = flowPassword;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SysUserDy getSysUserDy() {
		return sysUserDy;
	}

	public void setSysUserDy(SysUserDy sysUserDy) {
		this.sysUserDy = sysUserDy;
	}

	
}

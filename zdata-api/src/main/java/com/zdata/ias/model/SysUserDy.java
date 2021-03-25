package com.zdata.ias.model;

import java.io.Serializable;
import java.util.Date;

/**
*  sys_user_dy
* @author 梦丶随心飞 2020-08-04
*/
public class SysUserDy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Integer id;

    /**
    * open_id
    */
    private String openId;

    /**
    * user_id
    */
    private String userId;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 
     */
    private String accessToken;


    public SysUserDy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}

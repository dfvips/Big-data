package com.zdata.model;

import java.io.Serializable;
import java.util.Date;

/**
*  sys_log
* @author 梦丶随心飞 2020-07-21
*/
public class SysLog implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Integer id;

    /**
    * 用户id
    */
    private String userId;

    /**
    * 用户名称
    */
    private String userName;

    /**
    * 软件id
    */
    private String softId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * id地址
    */
    private String ipAddress;

    /**
    * 请求参数
    */
    private String params;

    /**
    * 请求地址
    */
    private String requestAddress;

    /**
    * 类型：1：登录日志；2：操作日志
    */
    private Integer type;


    public SysLog() {
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

    public String getSoftId() {
        return softId;
    }

    public void setSoftId(String softId) {
        this.softId = softId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

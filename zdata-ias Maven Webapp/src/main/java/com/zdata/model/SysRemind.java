package com.zdata.model;

import java.io.Serializable;

/**
 * 
 * @author 梦丶随心飞
 *
 */
public class SysRemind implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public SysRemind() {
		super();
	}
	
	public SysRemind(String userId,Integer softId) {
		super();
		this.userId=userId;
		this.softId=softId;
	}

    /**
    * id
    */
    private Integer id;

    /**
    * 菜单id
    */
    private String funcId;

    /**
    * 提醒数量
    */
    private Integer remindCount;

    /**
    * 用户id
    */
    private String userId;

    /**
    * 提醒名称
    */
    private String remindName;

    /**
    * 软件id
    */
    private Integer softId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public Integer getRemindCount() {
        return remindCount;
    }

    public void setRemindCount(Integer remindCount) {
        this.remindCount = remindCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemindName() {
        return remindName;
    }

    public void setRemindName(String remindName) {
        this.remindName = remindName;
    }

    public Integer getSoftId() {
        return softId;
    }

    public void setSoftId(Integer softId) {
        this.softId = softId;
    }
	
	

	
}

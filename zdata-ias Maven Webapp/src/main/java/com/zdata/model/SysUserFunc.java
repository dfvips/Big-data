package com.zdata.model;

import java.io.Serializable;

/**
*  sys_user_func
* @author 梦丶随心飞 2020-07-19
*/
public class SysUserFunc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Integer id;

    /**
    * user_id
    */
    private String userId;

    /**
    * func_id
    */
    private String funcId;


    public SysUserFunc() {
    	super();
    }
    
    public SysUserFunc(String userId,String funcId){
    	super();
    	this.userId = userId;
    	this.funcId = funcId;
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

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

}
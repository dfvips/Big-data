package com.zdata.model;

import java.io.Serializable;

/**
*  软件注册预设权限表
* @author 梦丶随心飞 2020-07-14
*/
public class SysSoftFunc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Integer id;

    /**
    * 功能id
    */
    private String funcId;

    /**
    * 软件id
    */
    private Integer softId;


    public SysSoftFunc() {
    }

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

    public Integer getSoftId() {
        return softId;
    }

    public void setSoftId(Integer softId) {
        this.softId = softId;
    }

}

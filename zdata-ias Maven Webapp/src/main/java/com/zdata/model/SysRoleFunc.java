package com.zdata.model;

/**
*  角色权限
* @author 梦丶随心飞 2020-07-07
*/
public class SysRoleFunc {
    /**
     * 
     */
    private Integer id;

    /**
     * 角色
     */
    private Integer roleId;

    /**
     * 功能Id
     */
    private String funcId;
    
    public SysRoleFunc(){
    	super();
    }
    
    public SysRoleFunc(Integer roleId,String funcId){
    	super();
    	this.roleId=roleId;
    	this.funcId=funcId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }
}
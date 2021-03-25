package com.zdata.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*  sys_func
* @author 梦丶随心飞 2020-07-21
*/
public class SysFunc implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private Integer id;

    /**
    * 节点id
    */
    private String funcId;

    /**
    * 节点名称
    */
    private String funcName;

    /**
    * 父节点id
    */
    private String funcParentId;

    /**
    * 显示顺序
    */
    private String funcSort;

    /**
    * 节点url
    */
    private String url;

    /**
    * 节点url基本
    */
    private String urlBasic;

    /**
    * 节点url参数
    */
    private String urlParam;

    /**
    * 菜单图标
    */
    private String funcImg;

    /**
    * 菜单类型:1:全url显示 2:基本url显示
    */
    private String funcType;

    /**
    * 菜单是否显示：0：否；1：是
    */
    private Integer isView;

    /**
    * 节点描述
    */
    private String funcDes;

    /**
    * 修改时间
    */
    private Date exchangTime;

    /**
    * 系统软件id参数名称
    */
    private String softParam;

    /**
    * 系统用户id参数名称
    */
    private String userParam;

    /**
    * 系统功能id参数名称
    */
    private String funcParam;

    /**
    * 菜单是否加密
    */
    private Integer isEncrypt;
    
    /**
     * 是否是父节点
     */
    private Integer isParent;
    
    private List<SysFunc> children;


    public SysFunc() {
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

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncParentId() {
        return funcParentId;
    }

    public void setFuncParentId(String funcParentId) {
        this.funcParentId = funcParentId;
    }

    public String getFuncSort() {
        return funcSort;
    }

    public void setFuncSort(String funcSort) {
        this.funcSort = funcSort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlBasic() {
        return urlBasic;
    }

    public void setUrlBasic(String urlBasic) {
        this.urlBasic = urlBasic;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getFuncImg() {
        return funcImg;
    }

    public void setFuncImg(String funcImg) {
        this.funcImg = funcImg;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public String getFuncDes() {
        return funcDes;
    }

    public void setFuncDes(String funcDes) {
        this.funcDes = funcDes;
    }

    public Date getExchangTime() {
        return exchangTime;
    }

    public void setExchangTime(Date exchangTime) {
        this.exchangTime = exchangTime;
    }

    public String getSoftParam() {
        return softParam;
    }

    public void setSoftParam(String softParam) {
        this.softParam = softParam;
    }

    public String getUserParam() {
        return userParam;
    }

    public void setUserParam(String userParam) {
        this.userParam = userParam;
    }

    public String getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    public Integer getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(Integer isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public List<SysFunc> getChildren() {
		return children;
	}

	public void setChildren(List<SysFunc> children) {
		this.children = children;
	}
    
}

package com.zdata.model;

import java.io.Serializable;
import java.util.Date;

import com.zdata.common.BaseParam;

/**
*  sys_soft
* @author 梦丶随心飞 2020-07-18
*/
public class SysSoft extends BaseParam implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private Integer id;

    /**
    * 软件id
    */
    private String softId;

    /**
    * 软件名称
    */
    private String softName;

    /**
    * 软件介绍
    */
    private String softIntro;

    /**
    * 版本
    */
    private String softVersion;

    /**
    * 软件拥有者
    */
    private String softCreate;

    /**
    * 建立时间
    */
    private Date createTime;

    /**
    * 网站地址
    */
    private String webUrl;
    
    /**
     * 系统首页
     */
    private String mainUrl;


    public SysSoft() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoftId() {
        return softId;
    }

    public void setSoftId(String softId) {
        this.softId = softId;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getSoftIntro() {
        return softIntro;
    }

    public void setSoftIntro(String softIntro) {
        this.softIntro = softIntro;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftCreate() {
        return softCreate;
    }

    public void setSoftCreate(String softCreate) {
        this.softCreate = softCreate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

    
}


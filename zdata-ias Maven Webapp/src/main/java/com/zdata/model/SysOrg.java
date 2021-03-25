package com.zdata.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 组织
 * <p>
 * Title: Org
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author 梦丶随心飞
 * @date 2019年6月9日
 */
public class SysOrg implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**
	 * 组号
	 */
	private String orgId;

	/**
	 * 名称
	 */
	private String orgName;

	/**
	 * 父节点
	 */
	private String orgParentId;

	/**
	 * 顺序
	 */
	private BigDecimal orgSort;

	/**
	 * 岗位
	 */
	private String position;

	/**
	 * 机构id
	 */
	private String cardId;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 管理用户id
	 */
	private String userId;

	/**
	 * 是否显示
	 */
	private String isShow;

	/**
	 * 修改日期
	 */
	private Date exchangDate;
	
	private Integer isParent;
	
	private List<SysOrg> children;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgParentId() {
		return orgParentId;
	}

	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}

	public BigDecimal getOrgSort() {
		return orgSort;
	}

	public void setOrgSort(BigDecimal orgSort) {
		this.orgSort = orgSort;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Date getExchangDate() {
		return exchangDate;
	}

	public void setExchangDate(Date exchangDate) {
		this.exchangDate = exchangDate;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public List<SysOrg> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrg> children) {
		this.children = children;
	}

}

package com.zdata.model;

import java.io.Serializable;
import java.util.List;

public class Ztree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String pId;
	private Integer isParent;
	private List<Ztree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Integer getIsParent() {
		return isParent;
	}
	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}
	public List<Ztree> getChildren() {
		return children;
	}
	public void setChildren(List<Ztree> children) {
		this.children = children;
	}
	

}

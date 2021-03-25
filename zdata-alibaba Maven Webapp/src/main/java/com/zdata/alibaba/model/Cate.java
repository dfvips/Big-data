package com.zdata.alibaba.model;

import java.util.List;

public class Cate {
    private Integer id;

    private Integer catId;

    private String catName;

    private Integer catParent;

    private Integer level;
    
    private List<Cate> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public Integer getCatParent() {
        return catParent;
    }

    public void setCatParent(Integer catParent) {
        this.catParent = catParent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

	public List<Cate> getChildren() {
		return children;
	}

	public void setChildren(List<Cate> children) {
		this.children = children;
	}
    
}
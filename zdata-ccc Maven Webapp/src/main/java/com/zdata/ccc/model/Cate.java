package com.zdata.ccc.model;

import java.util.List;

public class Cate {
    private Integer id;

    private Integer catId;

    private String catName;

    private Integer level;

    private Integer parentCatId;

    private String parentCatName;

    private Integer parentCatLevel;
    
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(Integer parentCatId) {
        this.parentCatId = parentCatId;
    }

    public String getParentCatName() {
        return parentCatName;
    }

    public void setParentCatName(String parentCatName) {
        this.parentCatName = parentCatName == null ? null : parentCatName.trim();
    }

    public Integer getParentCatLevel() {
        return parentCatLevel;
    }

    public void setParentCatLevel(Integer parentCatLevel) {
        this.parentCatLevel = parentCatLevel;
    }

	public List<Cate> getChildren() {
		return children;
	}

	public void setChildren(List<Cate> children) {
		this.children = children;
	}
	
}
package com.zdata.ccc.model;

import java.io.Serializable;
import java.util.List;

public class GoodsCate implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer catId;

    private String catName;

    private Integer level;

    private Integer parentCatId;
    
    private List<GoodsCate> children;

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

	public List<GoodsCate> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsCate> children) {
		this.children = children;
	}
    
}
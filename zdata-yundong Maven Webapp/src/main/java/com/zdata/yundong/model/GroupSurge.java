package com.zdata.yundong.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GroupSurge implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer catId;

    private String catName;

    private String goodsName;

    private Integer orderNum;

    private Integer visitorNum;

    private String incrPercent;

    private Date updateDate;

    private Integer type;
    
    private BigDecimal traning;
    
    private float related;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(Integer visitorNum) {
        this.visitorNum = visitorNum;
    }

    public String getIncrPercent() {
        return incrPercent;
    }

    public void setIncrPercent(String incrPercent) {
        this.incrPercent = incrPercent == null ? null : incrPercent.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public BigDecimal getTraning() {
		return traning;
	}

	public void setTraning(BigDecimal traning) {
		this.traning = traning;
	}

	public float getRelated() {
		return related;
	}

	public void setRelated(float related) {
		this.related = related;
	}
    
}
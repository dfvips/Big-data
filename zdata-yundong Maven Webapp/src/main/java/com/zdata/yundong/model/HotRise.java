package com.zdata.yundong.model;

import java.io.Serializable;
import java.util.Date;

public class HotRise implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String goodsName;

    private Integer hotGoodsId;

    private Integer visitorNum;

    private Integer orderNum;

    private Date riseDate;

    private String translation;

    private String visitorPercent;

    private String orderPercent;

    private String tag;

    private Integer catId;

    private String catName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getHotGoodsId() {
        return hotGoodsId;
    }

    public void setHotGoodsId(Integer hotGoodsId) {
        this.hotGoodsId = hotGoodsId;
    }

    public Integer getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(Integer visitorNum) {
        this.visitorNum = visitorNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getRiseDate() {
        return riseDate;
    }

    public void setRiseDate(Date riseDate) {
        this.riseDate = riseDate;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation == null ? null : translation.trim();
    }

    public String getVisitorPercent() {
        return visitorPercent;
    }

    public void setVisitorPercent(String visitorPercent) {
        this.visitorPercent = visitorPercent == null ? null : visitorPercent.trim();
    }

    public String getOrderPercent() {
        return orderPercent;
    }

    public void setOrderPercent(String orderPercent) {
        this.orderPercent = orderPercent == null ? null : orderPercent.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
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
}
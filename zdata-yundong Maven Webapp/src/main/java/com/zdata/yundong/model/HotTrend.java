package com.zdata.yundong.model;

import java.util.Date;

public class HotTrend {
    private Integer id;

    private Date updateDate;

    private Integer sell;

    private Integer comment;

    private Integer sumSell;

    private Integer goodNum;

    private Integer hotRiseId;

    private String normalPrice;

    private String groupPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getSumSell() {
        return sumSell;
    }

    public void setSumSell(Integer sumSell) {
        this.sumSell = sumSell;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getHotRiseId() {
        return hotRiseId;
    }

    public void setHotRiseId(Integer hotRiseId) {
        this.hotRiseId = hotRiseId;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice == null ? null : normalPrice.trim();
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice == null ? null : groupPrice.trim();
    }
}
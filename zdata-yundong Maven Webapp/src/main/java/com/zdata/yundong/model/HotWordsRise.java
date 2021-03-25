package com.zdata.yundong.model;

import java.util.Date;

public class HotWordsRise {
    private Integer id;

    private String word;

    private Integer hotWordsId;

    private Integer pv;

    private Integer clickNum;

    private String ctr;

    private String cvr;

    private Integer competeValue;

    private String imprAvgBid;

    private Integer catId;

    private String catName;

    private Date riseDate;

    private String pvPercent;

    private String clickNumPercent;
    
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public Integer getHotWordsId() {
        return hotWordsId;
    }

    public void setHotWordsId(Integer hotWordsId) {
        this.hotWordsId = hotWordsId;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr == null ? null : cvr.trim();
    }

    public Integer getCompeteValue() {
        return competeValue;
    }

    public void setCompeteValue(Integer competeValue) {
        this.competeValue = competeValue;
    }

    public String getImprAvgBid() {
        return imprAvgBid;
    }

    public void setImprAvgBid(String imprAvgBid) {
        this.imprAvgBid = imprAvgBid == null ? null : imprAvgBid.trim();
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

    public Date getRiseDate() {
        return riseDate;
    }

    public void setRiseDate(Date riseDate) {
        this.riseDate = riseDate;
    }

    public String getPvPercent() {
        return pvPercent;
    }

    public void setPvPercent(String pvPercent) {
        this.pvPercent = pvPercent == null ? null : pvPercent.trim();
    }

    public String getClickNumPercent() {
        return clickNumPercent;
    }

    public void setClickNumPercent(String clickNumPercent) {
        this.clickNumPercent = clickNumPercent == null ? null : clickNumPercent.trim();
    }

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
    
}
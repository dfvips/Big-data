package com.zdata.ccc.model;

import java.math.BigDecimal;
import java.util.Date;

public class HotWords {
    private Integer id;

    private Integer pv;

    private Integer clickNum;

    private String ctr;

    private String cvr;

    private Integer competeValue;

    private String imprAvgBid;

    private String word;

    private Integer catId;

    private String catName;

    private Date updateDate;
    
    /**
     * 访客占比
     * 名词解释：当天访客指数占当天该类目下总访客指数比值
     */
    private BigDecimal pvPercent;
    
    /**
     * 点击占比
     * 名词解释：当天点击指数占当天该类目下总点击指数比值
     */
    private BigDecimal clickPercent;
    
    /**
     * 关键词数量
     */
    private Integer wordCount;
    
    /**
     * 相似度
     */
    private float same;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public BigDecimal getPvPercent() {
		return pvPercent;
	}

	public void setPvPercent(BigDecimal pvPercent) {
		this.pvPercent = pvPercent;
	}

	public BigDecimal getClickPercent() {
		return clickPercent;
	}

	public void setClickPercent(BigDecimal clickPercent) {
		this.clickPercent = clickPercent;
	}

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public float getSame() {
		return same;
	}

	public void setSame(float same) {
		this.same = same;
	}
    
}
package com.zdata.sycm.model;

import java.io.Serializable;
import java.util.Date;

public class SearchWordSoar implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer catId;
    
    private String catName;

    private String clickHits;

    private String clickRate;

    private Integer rank;

    private Integer orderNum;

    private String p4pRefPrice;

    private String payRate;

    private String seIpvUvHits;

    private String searchWord;

    private String seRiseRate;

    private Date updateTime;

    private Date crawlTime;
    
    private String code;

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
		this.catName = catName;
	}

	public String getClickHits() {
        return clickHits;
    }

    public void setClickHits(String clickHits) {
        this.clickHits = clickHits == null ? null : clickHits.trim();
    }

    public String getClickRate() {
        return clickRate;
    }

    public void setClickRate(String clickRate) {
        this.clickRate = clickRate == null ? null : clickRate.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getP4pRefPrice() {
        return p4pRefPrice;
    }

    public void setP4pRefPrice(String p4pRefPrice) {
        this.p4pRefPrice = p4pRefPrice == null ? null : p4pRefPrice.trim();
    }

    public String getPayRate() {
        return payRate;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate == null ? null : payRate.trim();
    }

    public String getSeIpvUvHits() {
        return seIpvUvHits;
    }

    public void setSeIpvUvHits(String seIpvUvHits) {
        this.seIpvUvHits = seIpvUvHits == null ? null : seIpvUvHits.trim();
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord == null ? null : searchWord.trim();
    }

    public String getSeRiseRate() {
        return seRiseRate;
    }

    public void setSeRiseRate(String seRiseRate) {
        this.seRiseRate = seRiseRate == null ? null : seRiseRate.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
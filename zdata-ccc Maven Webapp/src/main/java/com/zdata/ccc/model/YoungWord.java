package com.zdata.ccc.model;

import java.util.List;

public class YoungWord{
    private Integer id;

    private String word;

    private Integer state;
    
    private String youngValue;
    
    private Integer catId;
    
    private String catName;
    
    private List<YoungTrend> youngTrends;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getYoungValue() {
		return youngValue;
	}

	public void setYoungValue(String youngValue) {
		this.youngValue = youngValue;
	}
	
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public List<YoungTrend> getYoungTrends() {
		return youngTrends;
	}

	public void setYoungTrends(List<YoungTrend> youngTrends) {
		this.youngTrends = youngTrends;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}
    
}
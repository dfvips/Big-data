package com.zdata.ccc.model;

import java.util.Date;

public class HotWordsCollect extends Page{
    private Integer id;

    private Integer wordRiseId;

    private Integer state;

    private Date collectDateTime;
    
    private HotWordsRise hotWordsRise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWordRiseId() {
        return wordRiseId;
    }

    public void setWordRiseId(Integer wordRiseId) {
        this.wordRiseId = wordRiseId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCollectDateTime() {
        return collectDateTime;
    }

    public void setCollectDateTime(Date collectDateTime) {
        this.collectDateTime = collectDateTime;
    }

	public HotWordsRise getHotWordsRise() {
		return hotWordsRise;
	}

	public void setHotWordsRise(HotWordsRise hotWordsRise) {
		this.hotWordsRise = hotWordsRise;
	}
    
    
}
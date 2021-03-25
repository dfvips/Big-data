package com.zdata.ccc.model;

import java.util.Date;

public class HotCollect extends Page{
    private Integer id;

    private Integer hotRiseId;

    private Integer state;

    private Date collectDateTime;
    
    private Date publishDate;

    private String url;
    
    private HotRise hotRise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotRiseId() {
        return hotRiseId;
    }

    public void setHotRiseId(Integer hotRiseId) {
        this.hotRiseId = hotRiseId;
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
    
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HotRise getHotRise() {
		return hotRise;
	}

	public void setHotRise(HotRise hotRise) {
		this.hotRise = hotRise;
	}
}
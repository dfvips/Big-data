package com.zdata.alibaba.model;

import java.util.Date;

public class HotRise {
    private Integer id;

    private Integer hotRankId;

    private Date riseDate;
    
    private HotRank hotRank;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotRankId() {
        return hotRankId;
    }

    public void setHotRankId(Integer hotRankId) {
        this.hotRankId = hotRankId;
    }

    public Date getRiseDate() {
        return riseDate;
    }

    public void setRiseDate(Date riseDate) {
        this.riseDate = riseDate;
    }

	public HotRank getHotRank() {
		return hotRank;
	}

	public void setHotRank(HotRank hotRank) {
		this.hotRank = hotRank;
	}
	
}
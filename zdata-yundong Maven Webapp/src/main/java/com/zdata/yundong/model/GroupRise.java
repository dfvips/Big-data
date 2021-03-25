package com.zdata.yundong.model;

import java.io.Serializable;
import java.util.Date;

public class GroupRise implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer groupId;

    private Date riseDate;
    
    private String goodUrl;
    
    private Integer isTag;
    
    private GroupSurge groupSurge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getRiseDate() {
        return riseDate;
    }

    public void setRiseDate(Date riseDate) {
        this.riseDate = riseDate;
    }
    
	public String getGoodUrl() {
		return goodUrl;
	}

	public void setGoodUrl(String goodUrl) {
		this.goodUrl = goodUrl;
	}

	public Integer getIsTag() {
		return isTag;
	}

	public void setIsTag(Integer isTag) {
		this.isTag = isTag;
	}

	public GroupSurge getGroupSurge() {
		return groupSurge;
	}

	public void setGroupSurge(GroupSurge groupSurge) {
		this.groupSurge = groupSurge;
	}
    
}
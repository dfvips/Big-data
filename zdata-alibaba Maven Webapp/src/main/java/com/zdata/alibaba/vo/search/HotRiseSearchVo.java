package com.zdata.alibaba.vo.search;

import com.zdata.alibaba.model.Page;

public class HotRiseSearchVo extends Page{

	private Integer catId;
	
	private String title;
	
	private String riseDate;
	
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRiseDate() {
		return riseDate;
	}

	public void setRiseDate(String riseDate) {
		this.riseDate = riseDate;
	}
	
	
}

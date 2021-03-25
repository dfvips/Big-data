package com.zdata.alibaba.vo.search;

import com.zdata.alibaba.model.Page;

public class HotWordsSearchVo extends Page{

	private Integer catId;
	
	private String keyword;
	
	private String period;

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	
}

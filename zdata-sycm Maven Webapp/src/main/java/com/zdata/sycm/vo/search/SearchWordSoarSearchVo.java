package com.zdata.sycm.vo.search;

import com.zdata.sycm.model.Page;

public class SearchWordSoarSearchVo extends Page{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer catId;
	
	private String updateTime;

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}

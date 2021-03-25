package com.zdata.ccc.vo.search;

import com.zdata.ccc.model.Page;

public class GroupRiseSearchVo extends Page{

	private Integer catId;
	
	private String goodsName;
	
	private String riseDate;

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getRiseDate() {
		return riseDate;
	}

	public void setRiseDate(String riseDate) {
		this.riseDate = riseDate;
	}
	
}

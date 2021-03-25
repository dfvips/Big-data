package com.zdata.yundong.vo.search;

import com.zdata.yundong.model.BaseParam;

public class GroupSurgeSearchVo extends BaseParam{
	
	private Integer catId;
	
	private String goodsName;
	
	private String updateDate;

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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}

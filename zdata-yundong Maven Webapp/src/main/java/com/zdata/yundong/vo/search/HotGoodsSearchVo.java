package com.zdata.yundong.vo.search;

import com.zdata.yundong.model.BaseParam;

public class HotGoodsSearchVo extends BaseParam{

	private Integer catId;
	
	private String goodsName;
	
	private String updateDate;
	
	public HotGoodsSearchVo(){
		super();
	}
	
	public HotGoodsSearchVo(Integer catId,String updateDate,String goodsName){
		super();
		this.catId = catId;
		this.updateDate = updateDate;
		this.goodsName = goodsName;
	}
	
	public HotGoodsSearchVo(String updateDate,String goodsName){
		super();
		this.updateDate = updateDate;
		this.goodsName = goodsName;
	}
	
	public HotGoodsSearchVo(Integer catId,String goodsName){
		super();
		this.catId = catId;
		this.goodsName = goodsName;
	}
	
	public HotGoodsSearchVo(String updateDate,Integer catId){
		super();
		this.catId = catId;
		this.updateDate = updateDate;
	}
	
	public HotGoodsSearchVo(String updateDate){
		super();
		this.updateDate = updateDate;
	}
	
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

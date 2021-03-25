package com.zdata.sycm.vo.search;

import com.zdata.sycm.model.Page;

public class HotSaleSearchVo extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer catId;
	
	private String itemId;
	
	private String updateTime;
	
	private String itemTitle;
	
	public HotSaleSearchVo(){
		super();
	}
	
	public HotSaleSearchVo(Integer catId,String updateTime){
		super();
		this.catId = catId;
		this.updateTime = updateTime;
	}
	
	public HotSaleSearchVo(Integer catId,String itemId,String updateTime){
		super();
		this.catId = catId;
		this.itemId = itemId;
		this.updateTime = updateTime;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	
}

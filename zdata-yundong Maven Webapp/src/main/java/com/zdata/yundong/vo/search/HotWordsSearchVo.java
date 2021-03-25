package com.zdata.yundong.vo.search;

import com.zdata.yundong.model.Page;

public class HotWordsSearchVo extends Page{

	private Integer catId;
	
	private String word;
	
	private String updateDate;
	
	public HotWordsSearchVo(){
		super();
	}
	
	public HotWordsSearchVo(String updateDate){
		super();
		this.updateDate=updateDate;
	}
	
	public HotWordsSearchVo(String updateDate,Integer catId){
		super();
		this.updateDate=updateDate;
		this.catId=catId;
	}
	
	public HotWordsSearchVo(String word,String updateDate){
		super();
		this.word = word;
		this.updateDate = updateDate;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}

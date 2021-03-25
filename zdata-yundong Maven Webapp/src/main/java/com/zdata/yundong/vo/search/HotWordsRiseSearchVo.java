package com.zdata.yundong.vo.search;

import com.zdata.yundong.model.Page;

public class HotWordsRiseSearchVo extends Page{

	private Integer catId;
	
	private String riseDate;
	
	private String word;
	
	public HotWordsRiseSearchVo(){
		super();
	}
	
	public HotWordsRiseSearchVo(String word,String riseDate){
		super();
		this.word = word;
		this.riseDate = riseDate;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getRiseDate() {
		return riseDate;
	}

	public void setRiseDate(String riseDate) {
		this.riseDate = riseDate;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}

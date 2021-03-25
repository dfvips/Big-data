package com.zdata.ccc.model;

public class DataInfo {

	private Integer value;
	
	private String name;

	public DataInfo(){
		super();
	}
	
	public DataInfo(Integer value,String name){
		super();
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

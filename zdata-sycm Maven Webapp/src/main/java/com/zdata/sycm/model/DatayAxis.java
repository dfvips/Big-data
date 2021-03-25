package com.zdata.sycm.model;

import java.math.BigDecimal;

public class DatayAxis {

	private String type;

	private String name;
	
	private Integer mix;
	
	private Integer max;
	
	private BigDecimal interval;
	
	public DatayAxis(String type,String name,Integer mix,Integer max){
		super();
		this.type=type;
		this.name=name;
		this.mix=mix;
		this.max=max;
	}

	public DatayAxis() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMix() {
		return mix;
	}

	public void setMix(Integer mix) {
		this.mix = mix;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public BigDecimal getInterval() {
		return interval;
	}

	public void setInterval(BigDecimal interval) {
		this.interval = interval;
	}
	
}

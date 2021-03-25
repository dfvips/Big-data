package com.zdata.yundong.model;

import java.math.BigDecimal;
import java.util.List;

public class Data {

	private String name;
	
	private String type;
	
	private Integer xAxisIndex;
	
	private Integer yAxisIndex;
	
	private BigDecimal[] data;
	
	private List<DataInfo> infos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getxAxisIndex() {
		return xAxisIndex;
	}

	public void setxAxisIndex(Integer xAxisIndex) {
		this.xAxisIndex = xAxisIndex;
	}

	public Integer getyAxisIndex() {
		return yAxisIndex;
	}

	public void setyAxisIndex(Integer yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
	}

	public BigDecimal[] getData() {
		return data;
	}

	public void setData(BigDecimal[] data) {
		this.data = data;
	}

	public List<DataInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<DataInfo> infos) {
		this.infos = infos;
	}
	
	
}

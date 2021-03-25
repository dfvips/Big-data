package com.zdata.ccc.model;

import java.util.List;

public class DataViews {

	private String[] legend;
	
	private List<Data> series;
	
	private List<DataxAxis> xAxis;
	
	private List<DatayAxis> yAxis;

	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	public List<Data> getSeries() {
		return series;
	}

	public void setSeries(List<Data> series) {
		this.series = series;
	}

	public List<DataxAxis> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<DataxAxis> xAxis) {
		this.xAxis = xAxis;
	}

	public List<DatayAxis> getyAxis() {
		return yAxis;
	}

	public void setyAxis(List<DatayAxis> yAxis) {
		this.yAxis = yAxis;
	}
	
}

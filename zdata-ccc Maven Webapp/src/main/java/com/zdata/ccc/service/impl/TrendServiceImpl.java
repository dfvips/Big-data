package com.zdata.ccc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.ccc.dao.TrendMapper;
import com.zdata.ccc.model.Data;
import com.zdata.ccc.model.DataViews;
import com.zdata.ccc.model.DataxAxis;
import com.zdata.ccc.model.Trend;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.TrendService;
import com.zdata.ccc.util.DateUtil;

@Service("trendService")
public class TrendServiceImpl implements TrendService {

	@Autowired
	private TrendMapper trendMapper;
	
	@Override
	public RetResult<?> findByCatId(Integer catId) throws Exception {
		List<Trend> list = trendMapper.findByCatId(catId);
		DataViews views = new DataViews();
		String[] legend = new String[]{"访客指数线图","搜索指数线图","点击指数线图"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		final List<Data> series = new ArrayList<Data>();
		for (int i = 0; i < legend.length; i++) {
			DataxAxis dataxAxis = new DataxAxis();
			String[] data = new String[list.size()];
			BigDecimal[] value = new BigDecimal[list.size()];
			for (int j = 0; j < list.size(); j++) {
				data[j] = DateUtil.formatDate(list.get(j).getUpdateDate(), "yyyy-MM-dd");
				if (legend[i].equals("访客指数线图")) {
					value[j] = new BigDecimal(list.get(j).getVisitorNum());
				}else if(legend[i].equals("搜索指数线图")){
					value[j] = new BigDecimal(list.get(j).getSearchNum());
				}else if(legend[i].equals("点击指数线图")){
					value[j] = new BigDecimal(list.get(j).getClickNum());
				}
			}
			dataxAxis.setData(data);
			xAxis.add(dataxAxis);
			Data seriesData = new Data();
			seriesData.setName(legend[i]);
			seriesData.setType("line");
			seriesData.setData(value);
			series.add(seriesData);
		}
		views.setxAxis(xAxis);
		views.setSeries(series);
		return RetResponse.makeOKRsp(views);
	}

}

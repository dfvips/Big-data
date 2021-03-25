package com.zdata.ccc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.ccc.dao.WordTrendMapper;
import com.zdata.ccc.model.Data;
import com.zdata.ccc.model.DataViews;
import com.zdata.ccc.model.DataxAxis;
import com.zdata.ccc.model.WordTrend;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.WordTrendService;
import com.zdata.ccc.util.DateUtil;
import com.zdata.ccc.vo.search.WordTrendSearchVo;

@Service("wordTrendService")
public class WordTrendServiceImpl implements WordTrendService {

	@Autowired
	private WordTrendMapper wordTrendMapper;
	
	@Override
	public RetResult<?> findDetail(WordTrendSearchVo searchVo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WordTrend> list = wordTrendMapper.find(searchVo);
		map.put("list", list);
		DataViews views = new DataViews();
		String[] legend = new String[]{"搜索热度线图","点击指数线图","竞争指数线图"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		final List<Data> series = new ArrayList<Data>();
		for (int i = 0; i < legend.length; i++) {
			DataxAxis dataxAxis = new DataxAxis();
			String[] data = new String[list.size()];
			BigDecimal[] value = new BigDecimal[list.size()];
			for (int j = 0; j < list.size(); j++) {
				data[j] = DateUtil.formatDate(list.get(j).getUpdateDate(), "yyyy-MM-dd");
				if (legend[i].equals("搜索热度线图")) {
					value[j] = new BigDecimal(list.get(j).getPv());
				}else if(legend[i].equals("点击指数线图")){
					value[j] = new BigDecimal(list.get(j).getClickNum());
				}else if(legend[i].equals("竞争指数线图")){
					value[j] = new BigDecimal(list.get(j).getCompeteValue());
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
		map.put("views", views);
		return RetResponse.makeOKRsp(map, list.size());
	}

}

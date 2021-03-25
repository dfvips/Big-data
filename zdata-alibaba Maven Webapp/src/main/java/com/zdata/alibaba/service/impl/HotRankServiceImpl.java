package com.zdata.alibaba.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.alibaba.dao.HotRankMapper;
import com.zdata.alibaba.model.Data;
import com.zdata.alibaba.model.DataViews;
import com.zdata.alibaba.model.DataxAxis;
import com.zdata.alibaba.model.HotRank;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.HotRankService;
import com.zdata.alibaba.util.DateUtil;
import com.zdata.alibaba.vo.search.HotRankSearchVo;

@Service
public class HotRankServiceImpl implements HotRankService {

	@Autowired
	private HotRankMapper hotRankMapper;
	
	@Override
	public RetResult<?> find(HotRankSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()==97) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotRank> list = hotRankMapper.find(searchVo);
		PageInfo<HotRank> info = new PageInfo<HotRank>(list);
		return RetResponse.makeOKRsp(list, (int) info.getTotal());
	}

	@Override
	public RetResult<?> findByOfferId(Long offerId,Long catId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<HotRank> list = hotRankMapper.findByOfferId(offerId,catId);
		DataViews views = new DataViews();
		String[] legend = new String[]{"交易指数趋势","流量指数趋势"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		final List<Data> series = new ArrayList<Data>();
		for (int i = 0; i < legend.length; i++) {
			DataxAxis dataxAxis = new DataxAxis();
			String[] data = new String[list.size()];
			BigDecimal[] value = new BigDecimal[list.size()];
			for (int j = 0; j < list.size(); j++) {
				data[j] = DateUtil.formatDate(list.get(j).getCrawlTime(), "yyyy-MM-dd");
				if (legend[i].equals("交易指数趋势")) {
					value[j] = new BigDecimal(list.get(j).getTrade()==null?0:list.get(j).getTrade());
				}else if(legend[i].equals("流量指数趋势")){
					value[j] = new BigDecimal(list.get(j).getFlow()==null?0:list.get(j).getFlow());
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
		map.put("list", list);
		return RetResponse.makeOKRsp(map,list.size());
	}

}

package com.zdata.ccc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.zdata.ccc.dao.ConstantMapper;
import com.zdata.ccc.dao.YoungTrendMapper;
import com.zdata.ccc.dao.YoungWordMapper;
import com.zdata.ccc.model.Constant;
import com.zdata.ccc.model.Data;
import com.zdata.ccc.model.DataViews;
import com.zdata.ccc.model.DataxAxis;
import com.zdata.ccc.model.YoungTrend;
import com.zdata.ccc.model.YoungWord;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.YoungWordService;
import com.zdata.ccc.util.DateUtil;

@Service("youngWordService")
public class YoungWordServiceImpl implements YoungWordService {

	Logger log = LoggerFactory.getLogger(YoungWordServiceImpl.class);
	
	@Autowired
	private YoungWordMapper youngWordMapper;
	
	@Autowired
	private ConstantMapper constantMapper;
	
	@Autowired
	private YoungTrendMapper youngTrendMapper;
	
	@Override
	public RetResult<?> find(YoungWord record) throws Exception {
		if (record.getCatId()==2933) {
			record.setCatId(null);
		}
		List<YoungWord> list = youngWordMapper.find(record);
		return RetResponse.makeOKRsp(list, list.size());
	}

	@Override
	public RetResult<?> findDetail(Integer id) throws Exception {
		List<YoungTrend> list = youngTrendMapper.findByWordId(id);
		DataViews views = new DataViews();
		String[] legend = new String[]{"竞争指数线图","搜索指数线图","点击指数线图"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		final List<Data> series = new ArrayList<Data>();
		for (int i = 0; i < legend.length; i++) {
			DataxAxis dataxAxis = new DataxAxis();
			String[] data = new String[list.size()];
			BigDecimal[] value = new BigDecimal[list.size()];
			for (int j = 0; j < list.size(); j++) {
				data[j] = DateUtil.formatDate(list.get(j).getUpdateDate(), "yyyy-MM-dd");
				if (legend[i].equals("竞争指数线图")) {
					value[j] = new BigDecimal(list.get(j).getCompeteValue());
				}else if(legend[i].equals("搜索指数线图")){
					value[j] = new BigDecimal(list.get(j).getPv());
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

	@Override
	public RetResult<?> deleteById(Integer id) throws Exception {
		YoungWord youngWord = youngWordMapper.selectByPrimaryKey(id);
		if (youngWord!=null) {
			//判断常量表是否存在数据
			Constant constant = constantMapper.selectByWord(youngWord.getWord());
			if (constant==null) {
				Constant record = new Constant();
				record.setWord(youngWord.getWord());
				record.setState(0);
				constantMapper.insert(record);
			}
			youngTrendMapper.deleteByWordId(youngWord.getId());
			youngWordMapper.deleteByPrimaryKey(youngWord.getId());
		}
		return RetResponse.makeOKRsp();
	}

	
	
}

package com.zdata.sycm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.sycm.dao.CateMapper;
import com.zdata.sycm.dao.HotSearchMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.model.Data;
import com.zdata.sycm.model.DataViews;
import com.zdata.sycm.model.DataxAxis;
import com.zdata.sycm.model.HotSearch;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotSearchService;
import com.zdata.sycm.service.RedisService;
import com.zdata.sycm.util.DateUtil;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

@Service("hotSearchService")
public class HotSearchServiceImpl implements HotSearchService {

	@Autowired
	private HotSearchMapper hotSearchMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CateMapper cateMapper;
	
	@Override
	public RetResult<?> find(HotSearchSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotSearch> list = hotSearchMapper.find(searchVo);
		PageInfo<HotSearch> info = new PageInfo<HotSearch>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findHot(HotSearchSearchVo searchVo) throws Exception {
		List<HotSearch> result = new ArrayList<HotSearch>(); 
		String key = "sycm_hotsearch_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		if (redisService.hasKey(key)) {
			//缓存存在
			String value = redisService.getString(key);
			result = JSON.parseArray(value,HotSearch.class);
		} else {
			result = getByCatId(searchVo);
			String value = JSON.toJSONString(result);
			redisService.setStringAndTimeOut(key, value, 43200);
		}
		return RetResponse.makeOKRsp(result, result.size());
	}

	private List<HotSearch> getByCatId(HotSearchSearchVo searchVo) {
		List<HotSearch> result = new ArrayList<HotSearch>();
		if (searchVo!=null&&searchVo.getCatId()==1801) {
			List<Cate> cates = cateMapper.find();
			for (int j = 0; j < cates.size(); j++) {
				searchVo.setCatId(cates.get(j).getCatId());
				List<HotSearch> list = hotSearchMapper.findHot(searchVo);
				Iterator<HotSearch> iterator = list.iterator();
				while(iterator.hasNext()){
					HotSearch hotSearch = iterator.next();
					HotSearchSearchVo item = new HotSearchSearchVo(hotSearch.getCatId(), hotSearch.getItemId(), DateUtil.formatDate(hotSearch.getUpdateTime(), "yyyy-MM-dd"));
					List<HotSearch> itemList = hotSearchMapper.findByItemId(item);
					BigDecimal[] l = new BigDecimal[itemList.size()];
					for (int i = 0; i < itemList.size(); i++) {
						l[i] = new BigDecimal(itemList.get(i).getUvIndex());//流量指数
					}
					boolean falg = false;
					for (int i = 0; i < l.length-1; i++) {
						//大于等于
						if (l[i].compareTo(l[i+1])!=-1) {
							falg = true;
							break;
						}
					}
					if (falg) {
						iterator.remove();
					}
				}
				result.addAll(list);
			}
		}else{
			result = hotSearchMapper.findHot(searchVo);
			Iterator<HotSearch> iterator = result.iterator();
			while(iterator.hasNext()){
				HotSearch hotSearch = iterator.next();
				HotSearchSearchVo item = new HotSearchSearchVo(hotSearch.getCatId(), hotSearch.getItemId(), DateUtil.formatDate(hotSearch.getUpdateTime(), "yyyy-MM-dd"));
				List<HotSearch> itemList = hotSearchMapper.findByItemId(item);
				BigDecimal[] l = new BigDecimal[itemList.size()];
				for (int i = 0; i < itemList.size(); i++) {
					l[i] = new BigDecimal(itemList.get(i).getUvIndex());//流量指数
				}
				boolean falg = false;
				for (int i = 0; i < l.length-1; i++) {
					//大于等于
					if (l[i].compareTo(l[i+1])!=-1) {
						falg = true;
						break;
					}
				}
				if (falg) {
					iterator.remove();
				}
			}
		}
		return result;
	}

	@Override
	public RetResult<?> findDetail(HotSearchSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotSearch> list = hotSearchMapper.find(searchVo);
		PageInfo<HotSearch> info = new PageInfo<HotSearch>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findLine(HotSearchSearchVo searchVo) throws Exception {
		String key = "sycm_hotsearch_line_"+searchVo.getCatId()+"_"+searchVo.getItemId();
		DataViews views = new DataViews();
		if (redisService.hasKey(key)) {
			String value = redisService.getString(key);
			views = JSONObject.parseObject(value,DataViews.class);
		}else{
			List<HotSearch> list = hotSearchMapper.find(searchVo);
			if (list!=null&&list.size()>0) {
				HotSearch record = list.get(0);
				//反转list
				Collections.reverse(list);
				final String[] legend = new String[]{"【排名】"+record.getItemTitle(),"【交易指数】"+record.getItemTitle(),"【搜索人气】"+record.getItemTitle(),"【流量指数】"+record.getItemTitle()};
				views.setLegend(legend);
				final List<DataxAxis> dataxAxis = new ArrayList<DataxAxis>();
				final List<Data> series = new ArrayList<Data>();
				for (int j = 0; j < legend.length; j++) {
					DataxAxis axis = new DataxAxis();
					Data item = new Data();
					item.setType("line");
					item.setName(legend[j]);
					String[] data = new String[list.size()];
					BigDecimal[] values = new BigDecimal[list.size()];
					for (int i = 0; i < list.size(); i++) {
						data[i] = DateUtil.formatDate(list.get(i).getUpdateTime(), "yyyy-MM-dd");
						if (legend[j].equals("【排名】"+record.getItemTitle())) {
							values[i] = new BigDecimal(list.get(i).getCateRank());
						}else if(legend[j].equals("【交易指数】"+record.getItemTitle())){
							values[i] = new BigDecimal(list.get(i).getTradeIndex());
						}else if(legend[j].equals("【搜索人气】"+record.getItemTitle())){
							values[i] = new BigDecimal(list.get(i).getSeIpvUvHits());
						}else if(legend[j].equals("【流量指数】"+record.getItemTitle())){
							values[i] = new BigDecimal(list.get(i).getUvIndex());
						}
					}
					axis.setData(data);
					item.setData(values);
					dataxAxis.add(axis);
					series.add(item);
				}
				views.setxAxis(dataxAxis);
				views.setSeries(series);
			}
			String value = JSONObject.toJSONString(views);
			redisService.setStringAndTimeOut(key, value, 43200);
		}
		
		return RetResponse.makeOKRsp(views);
	}

	@Override
	public RetResult<?> delCache(HotSearchSearchVo searchVo) throws Exception {
		String key = "sycm_hotsearch_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		redisService.delKey(key);
		String keys = "sycm_hotsearch_line_"+searchVo.getCatId()+"_";
		redisService.delKeys(keys);
		return RetResponse.makeOKRsp();
	}

}

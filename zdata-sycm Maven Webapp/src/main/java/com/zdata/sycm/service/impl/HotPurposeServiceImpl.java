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
import com.zdata.sycm.dao.HotPurposeMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.model.Data;
import com.zdata.sycm.model.DataViews;
import com.zdata.sycm.model.DataxAxis;
import com.zdata.sycm.model.HotPurpose;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotPurposeService;
import com.zdata.sycm.service.RedisService;
import com.zdata.sycm.util.DateUtil;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;

@Service("hotPurposeService")
public class HotPurposeServiceImpl implements HotPurposeService {

	@Autowired
	private HotPurposeMapper hotPurposeMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CateMapper cateMapper;
	
	@Override
	public RetResult<?> find(HotPurposeSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotPurpose> list = hotPurposeMapper.find(searchVo);
		PageInfo<HotPurpose> info = new PageInfo<HotPurpose>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findHot(HotPurposeSearchVo searchVo) throws Exception {
		List<HotPurpose> result = new ArrayList<HotPurpose>(); 
		//大类情况
		String key = "sycm_hotpurpose_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		if (redisService.hasKey(key)) {
			//缓存存在
			String value = redisService.getString(key);
			result = JSON.parseArray(value,HotPurpose.class);
		} else {
			result = getByCatId(searchVo);
			String value = JSON.toJSONString(result);
			redisService.setStringAndTimeOut(key, value, 43200);
		}
		return RetResponse.makeOKRsp(result, result.size());
	}

	private List<HotPurpose> getByCatId(HotPurposeSearchVo searchVo) {
		List<HotPurpose> result = new ArrayList<HotPurpose>();
		if (searchVo!=null&&searchVo.getCatId()==1801) {
			List<Cate> cates = cateMapper.find();
			for (int j = 0; j < cates.size(); j++) {
				searchVo.setCatId(cates.get(j).getCatId());
				List<HotPurpose> list = hotPurposeMapper.findHot(searchVo);
				Iterator<HotPurpose> iterator = list.iterator();
				while(iterator.hasNext()){
					HotPurpose hotSale = iterator.next();
					HotPurposeSearchVo item = new HotPurposeSearchVo(hotSale.getCatId(), hotSale.getItemId(), DateUtil.formatDate(hotSale.getUpdateTime(), "yyyy-MM-dd"));
					List<HotPurpose> itemList = hotPurposeMapper.findByItemId(item);
					for (int i = 0; i < itemList.size(); i++) {
						if (itemList.get(i).getCateRankCqc()==null) {
							iterator.remove();
							break;
						}else{
							//大于0的数据移除
							if(itemList.get(i).getCateRankCqc()>0){
								iterator.remove();
								break;
							}
						}
					}
				}
				result.addAll(list);
			}
		}else{
			result = hotPurposeMapper.findHot(searchVo);
			Iterator<HotPurpose> iterator = result.iterator();
			while(iterator.hasNext()){
				HotPurpose hotSale = iterator.next();
				HotPurposeSearchVo item = new HotPurposeSearchVo(hotSale.getCatId(), hotSale.getItemId(), DateUtil.formatDate(hotSale.getUpdateTime(), "yyyy-MM-dd"));
				List<HotPurpose> itemList = hotPurposeMapper.findByItemId(item);
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getCateRankCqc()==null) {
						iterator.remove();
						break;
					}else{
						//大于0的数据移除
						if(itemList.get(i).getCateRankCqc()>0){
							iterator.remove();
							break;
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public RetResult<?> findDetail(HotPurposeSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotPurpose> list = hotPurposeMapper.find(searchVo);
		PageInfo<HotPurpose> info = new PageInfo<HotPurpose>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findLine(HotPurposeSearchVo searchVo) throws Exception {
		String key = "sycm_hotpurpose_line_"+searchVo.getCatId()+"_"+searchVo.getItemId();
		DataViews views = new DataViews();
		if (redisService.hasKey(key)) {
			String value = redisService.getString(key);
			views = JSONObject.parseObject(value,DataViews.class);
		}else{
			List<HotPurpose> list = hotPurposeMapper.find(searchVo);
			if (list!=null&&list.size()>0) {
				HotPurpose record = list.get(0);
				//反转list
				Collections.reverse(list);
				final String[] legend = new String[]{"【排名】"+record.getItemTitle(),"【交易指数】"+record.getItemTitle(),"【收藏人气】"+record.getItemTitle(),"【加购人气】"+record.getItemTitle()};
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
						}else if(legend[j].equals("【收藏人气】"+record.getItemTitle())){
							values[i] = new BigDecimal(list.get(i).getCltHits());
						}else if(legend[j].equals("【加购人气】"+record.getItemTitle())){
							values[i] = new BigDecimal(list.get(i).getCartHits());
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
	public RetResult<?> delCache(HotPurposeSearchVo searchVo) throws Exception {
		String key = "sycm_hotpurpose_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		redisService.delKey(key);
		String keys = "sycm_hotpurpose_line_"+searchVo.getCatId()+"_";
		redisService.delKeys(keys);
		return RetResponse.makeOKRsp();
	}

}

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
import com.zdata.sycm.dao.HotSaleMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.model.Data;
import com.zdata.sycm.model.DataViews;
import com.zdata.sycm.model.DataxAxis;
import com.zdata.sycm.model.DatayAxis;
import com.zdata.sycm.model.HotSale;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotSaleService;
import com.zdata.sycm.service.RedisService;
import com.zdata.sycm.util.DateUtil;
import com.zdata.sycm.vo.search.HotSaleSearchVo;

@Service("hotSaleService")
public class HotSaleServiceImpl implements HotSaleService {

	@Autowired
	private HotSaleMapper hotSaleMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CateMapper cateMapper;
	
	@Override
	public RetResult<?> find(HotSaleSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotSale> list = hotSaleMapper.find(searchVo);
		PageInfo<HotSale> info = new PageInfo<HotSale>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findHot(HotSaleSearchVo searchVo) throws Exception {
		List<HotSale> result = new ArrayList<HotSale>(); 
		String key = "sycm_hotsale_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		if (redisService.hasKey(key)) {
			//缓存存在
			String value = redisService.getString(key);
			result = JSON.parseArray(value,HotSale.class);
		} else {
			result = getByCatId(searchVo);
			String value = JSON.toJSONString(result);
			redisService.setStringAndTimeOut(key, value, 43200);
		}
		return RetResponse.makeOKRsp(result, result.size());
	}
	
	private List<HotSale> getByCatId(HotSaleSearchVo searchVo){
		List<HotSale> result = new ArrayList<HotSale>();
		if (searchVo!=null&&searchVo.getCatId()==1801) {
			List<Cate> cates = cateMapper.find();
			for (int j = 0; j < cates.size(); j++) {
				searchVo.setCatId(cates.get(j).getCatId());
				List<HotSale> list = hotSaleMapper.findHot(searchVo);
				Iterator<HotSale> iterator = list.iterator();
				while(iterator.hasNext()){
					HotSale hotSale = iterator.next();
					HotSaleSearchVo item = new HotSaleSearchVo(hotSale.getCatId(), hotSale.getItemId(), DateUtil.formatDate(hotSale.getUpdateTime(), "yyyy-MM-dd"));
					List<HotSale> itemList = hotSaleMapper.findByItemId(item);
					for (int i = 0; i < itemList.size(); i++) {
						if (itemList.get(i).getCateRankCqc()==null) {
							iterator.remove();
							break;
						}else{
							//大于等于0的数据移除
							if(itemList.get(i).getCateRankCqc()>=0){
								iterator.remove();
								break;
							}
						}
					}
				}
				result.addAll(list);
			}
		}else{
			result = hotSaleMapper.findHot(searchVo);
			Iterator<HotSale> iterator = result.iterator();
			while(iterator.hasNext()){
				HotSale hotSale = iterator.next();
				HotSaleSearchVo item = new HotSaleSearchVo(hotSale.getCatId(), hotSale.getItemId(), DateUtil.formatDate(hotSale.getUpdateTime(), "yyyy-MM-dd"));
				List<HotSale> itemList = hotSaleMapper.findByItemId(item);
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getCateRankCqc()==null) {
						iterator.remove();
						break;
					}else{
						//大于等于0的数据移除
						if(itemList.get(i).getCateRankCqc()>=0){
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
	public RetResult<?> findDetail(HotSaleSearchVo searchVo) throws Exception {
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotSale> list = hotSaleMapper.find(searchVo);
		PageInfo<HotSale> info = new PageInfo<HotSale>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findLine(HotSaleSearchVo searchVo) throws Exception {
		String key = "sycm_hotsale_line_"+searchVo.getCatId()+"_"+searchVo.getItemId(); 
		DataViews views = new DataViews();
		if (redisService.hasKey(key)) {
			String value = redisService.getString(key);
			views = JSONObject.parseObject(value,DataViews.class);
		}else{
			List<HotSale> list = hotSaleMapper.find(searchVo);
			if (list!=null&&list.size()>0) {
				HotSale record = list.get(0);
				//反转list
				Collections.reverse(list);
				final String[] legend = new String[]{"【排名】"+record.getItemTitle(),"【交易指数】"+record.getItemTitle(),"【支付转化指数】"+record.getItemTitle()};
				views.setLegend(legend);
				final List<DataxAxis> dataxAxis = new ArrayList<DataxAxis>();
				final List<Data> series = new ArrayList<Data>();
				final List<DatayAxis> datayAxis = new ArrayList<DatayAxis>();
				for (int j = 0; j < legend.length; j++) {
					DataxAxis axis = new DataxAxis();
					DatayAxis yAxis = new DatayAxis();
					Data item = new Data();
					item.setType("line");
					item.setName(legend[j]);
					String[] data = new String[list.size()];
					BigDecimal[] values = new BigDecimal[list.size()];
					for (int i = 0; i < list.size(); i++) {
						data[i] = DateUtil.formatDate(list.get(i).getUpdateTime(), "yyyy-MM-dd");
						if (legend[j].equals("【排名】"+record.getItemTitle())) {
							yAxis.setInterval(new BigDecimal(1));
							values[i] = new BigDecimal(list.get(i).getCateRank());
						}else if(legend[j].equals("【交易指数】"+record.getItemTitle())){
							yAxis.setInterval(new BigDecimal(5000));
							values[i] = new BigDecimal(list.get(i).getTradeIndex());
						}else if(legend[j].equals("【支付转化指数】"+record.getItemTitle())){
							yAxis.setInterval(new BigDecimal(100));
							values[i] = new BigDecimal(list.get(i).getPayRateIndex()==null?"0":list.get(i).getPayRateIndex());
						}
					}
					axis.setData(data);
					item.setData(values);
					dataxAxis.add(axis);
					datayAxis.add(yAxis);
					series.add(item);
				}
				views.setxAxis(dataxAxis);
				views.setyAxis(datayAxis);
				views.setSeries(series);
			}
			String value = JSONObject.toJSONString(views);
			redisService.setStringAndTimeOut(key, value, 43200);
		}
		return RetResponse.makeOKRsp(views);
	}

	@Override
	public RetResult<?> delCache(HotSaleSearchVo searchVo) throws Exception {
		String key = "sycm_hotsale_"+searchVo.getCatId()+"_"+searchVo.getUpdateTime();
		redisService.delKey(key);
		String keys = "sycm_hotsale_line_"+searchVo.getCatId()+"_";
		redisService.delKeys(keys);
		return RetResponse.makeOKRsp();
	}

}

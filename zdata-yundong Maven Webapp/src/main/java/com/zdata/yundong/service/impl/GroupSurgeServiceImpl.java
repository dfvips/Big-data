package com.zdata.yundong.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdata.yundong.dao.GroupSurgeMapper;
import com.zdata.yundong.dao.TrendMapper;
import com.zdata.yundong.model.Data;
import com.zdata.yundong.model.DataViews;
import com.zdata.yundong.model.DataxAxis;
import com.zdata.yundong.model.GroupSurge;
import com.zdata.yundong.model.Trend;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.GroupSurgeService;
import com.zdata.yundong.util.DateUtil;
import com.zdata.yundong.util.SimilarityUtil;
import com.zdata.yundong.vo.search.GroupSurgeSearchVo;

@Service("groupSurgeService")
public class GroupSurgeServiceImpl implements GroupSurgeService {

	@Autowired
	private GroupSurgeMapper groupSurgeMapper;
	
	@Autowired
	private TrendMapper trendMapper;
	
	@Override
	public RetResult<?> findDetail(GroupSurgeSearchVo searchVo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GroupSurge> list = groupSurgeMapper.findListByCatId(searchVo);
		map.put("list", list);
		DataViews views = new DataViews();
		String[] legend = new String[]{"访客线图","订单线图","访客大盘占比线图"};
		views.setLegend(legend);
		final List<Data> series = new ArrayList<Data>();
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		for (int i = 0; i < legend.length; i++) {
			DataxAxis dataxAxis = new DataxAxis();
			Data data = new Data();
			data.setName(legend[i]);
			data.setType("line");
			String[] date = new String[list.size()];
			BigDecimal[] value = new BigDecimal[list.size()];
			for (int j = 0; j < list.size(); j++) {
				date[j] = DateUtil.formatDate(list.get(j).getUpdateDate(), "yyyy-MM-dd");
				if (legend[i].equals("访客线图")) {
					value[j] = new BigDecimal(list.get(j).getVisitorNum());
				}else if(legend[i].equals("订单线图")){
					value[j] = new BigDecimal(list.get(j).getOrderNum());
				}else if(legend[i].equals("访客大盘占比线图")){
					Trend trend = trendMapper.selectByDate(DateUtil.formatDate(list.get(j).getUpdateDate(), "yyyy-MM-dd"), list.get(j).getCatId());
					if (trend!=null) {
						value[j] = divide(new BigDecimal(list.get(j).getVisitorNum()), new BigDecimal(trend.getVisitorNum()), 6);
					}
				}
			}
			dataxAxis.setData(date);
			data.setData(value);
			xAxis.add(dataxAxis);
			series.add(data);
		}
		views.setxAxis(xAxis);
		views.setSeries(series);
		map.put("views", views);
		return RetResponse.makeOKRsp(map,list.size());
	}

	@Override
	public RetResult<?> findListByCatId(GroupSurgeSearchVo searchVo) throws Exception {
		PageMethod.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<GroupSurge> list = groupSurgeMapper.findListByCatId(searchVo);
		for (GroupSurge groupSurge : list) {
			BigDecimal order = new BigDecimal(groupSurge.getOrderNum());
			BigDecimal visitor = new BigDecimal(groupSurge.getVisitorNum());
			//计算转化
			BigDecimal result = order.divide(visitor, 2, BigDecimal.ROUND_HALF_UP);
			groupSurge.setTraning(result);
		}
		PageInfo<GroupSurge> info = new PageInfo<GroupSurge>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findRelated(GroupSurgeSearchVo searchVo) throws Exception {
		String goodsName = searchVo.getGoodsName();
		searchVo.setGoodsName(null);
		List<GroupSurge> list = groupSurgeMapper.findListByCatId(searchVo);
		List<GroupSurge> surges = new ArrayList<GroupSurge>();
		for (int i = 0; i < list.size(); i++) {
			float related = SimilarityUtil.getSimilarity(goodsName, list.get(i).getGoodsName());
			if (related>0.65) {
				list.get(i).setRelated(related);
				surges.add(list.get(i));
			}
		}
		return RetResponse.makeOKRsp(surges, surges.size());
	}
	
	/**
	 * 除法计算：num为保留多少位小数
	 * @param:        @param fenzi
	 * @param:        @param fenmu
	 * @param:        @param num
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月19日 下午5:10:54 
	 */
	private BigDecimal divide(BigDecimal fenzi,BigDecimal fenmu,Integer num){
		BigDecimal result = fenzi.divide(fenmu, num, BigDecimal.ROUND_HALF_UP);
		return result;
	}

}

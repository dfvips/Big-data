package com.zdata.ccc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.ccc.dao.GroupRiseMapper;
import com.zdata.ccc.dao.GroupSurgeMapper;
import com.zdata.ccc.dao.TrendMapper;
import com.zdata.ccc.model.Data;
import com.zdata.ccc.model.DataViews;
import com.zdata.ccc.model.DataxAxis;
import com.zdata.ccc.model.GroupRise;
import com.zdata.ccc.model.GroupSurge;
import com.zdata.ccc.model.Trend;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.GroupRiseService;
import com.zdata.ccc.util.DateUtil;
import com.zdata.ccc.vo.search.GroupRiseSearchVo;

@Service("groupRiseService")
public class GroupRiseServiceImpl implements GroupRiseService{

	@Autowired
	private GroupSurgeMapper groupSurgeMapper;
	
	@Autowired
	private GroupRiseMapper groupRiseMapper;
	
	@Autowired
	private TrendMapper trendMapper;
	
	@Override
	public void updateRise(String riseDate) throws Exception {
		List<GroupSurge> list = groupSurgeMapper.findByUpdateDate(riseDate);
		for (int i = 0; i < list.size(); i++) {
			GroupRise groupRise = groupRiseMapper.selectByGroupId(list.get(i).getId());
			if (groupRise==null) {
				GroupRise record = new GroupRise();
				record.setRiseDate(list.get(i).getUpdateDate());
				record.setGroupId(list.get(i).getId());
				groupRiseMapper.insert(record);
			}
		}
	}

	@Override
	public RetResult<?> find(GroupRiseSearchVo searchVo) throws Exception {
		if (searchVo!=null&&searchVo.getCatId()==2933) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<GroupRise> list = groupRiseMapper.find(searchVo);
		PageInfo<GroupRise> info = new PageInfo<GroupRise>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> save(GroupRise record) throws Exception {
		if (record.getId()==null) {
			groupRiseMapper.insert(record);
		}else{
			groupRiseMapper.updateByPrimaryKey(record);
		}
		return RetResponse.makeOKRsp();
	}

	@Override
	public RetResult<?> updateTag(GroupRise record) throws Exception {
		GroupRise groupRise = groupRiseMapper.selectByPrimaryKey(record.getId());
		groupRise.setIsTag(record.getIsTag());
		groupRiseMapper.updateByPrimaryKey(groupRise);
		return RetResponse.makeOKRsp();
	}

	@Override
	public RetResult<?> findLine(GroupRiseSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==2933) {
			searchVo.setCatId(null);
		}
		List<GroupRise> list = groupRiseMapper.find(searchVo);
		DataViews views = new DataViews();
		final List<String> title = new ArrayList<String>();
		final List<Data> series = new ArrayList<Data>();
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		for (int i = 0; i < list.size(); i++) {
			GroupSurge record = list.get(i).getGroupSurge();
			List<GroupSurge> surges = groupSurgeMapper.findByGoodsName(record.getGoodsName());
			//上榜超过一天数据
			if (surges!=null&&surges.size()>1) {
				if(surges.get(0).getVisitorNum()<surges.get(surges.size()-1).getVisitorNum()){
					title.add(record.getGoodsName());
					Data data = new Data();
					DataxAxis dataxAxis = new DataxAxis();
					data.setName(record.getGoodsName());
					data.setType("line");
					//大盘趋势
					final BigDecimal[] value = new BigDecimal[surges.size()];
					final String[] date = new String[surges.size()];
					for (int j = 0; j < surges.size(); j++) {
						Trend trend = trendMapper.selectByDate(DateUtil.formatDate(surges.get(j).getUpdateDate(), "yyyy-MM-dd"), record.getCatId());
						if (trend!=null) {
							value[j] = divide(new BigDecimal(surges.get(j).getVisitorNum()), new BigDecimal(trend.getVisitorNum()), 6);
						} else {
							value[j] = BigDecimal.ZERO;
						}
						date[j] = DateUtil.formatDate(surges.get(j).getUpdateDate(), "yyyy-MM-dd");
					}
					data.setData(value);
					series.add(data);
					dataxAxis.setData(date);
					xAxis.add(dataxAxis);
				}
			}
		}
		final String[] legend = new String[title.size()];
		views.setLegend(title.toArray(legend));
		views.setSeries(series);
		views.setxAxis(xAxis);
		return RetResponse.makeOKRsp(views);
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

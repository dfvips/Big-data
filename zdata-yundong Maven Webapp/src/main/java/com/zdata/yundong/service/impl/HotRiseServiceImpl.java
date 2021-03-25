package com.zdata.yundong.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zdata.yundong.dao.HotGoodsMapper;
import com.zdata.yundong.dao.HotRiseMapper;
import com.zdata.yundong.dao.TrendMapper;
import com.zdata.yundong.model.Data;
import com.zdata.yundong.model.DataViews;
import com.zdata.yundong.model.DataxAxis;
import com.zdata.yundong.model.HotGoods;
import com.zdata.yundong.model.HotRise;
import com.zdata.yundong.model.Trend;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotGoodsService;
import com.zdata.yundong.service.HotRiseService;
import com.zdata.yundong.util.DateUtil;
import com.zdata.yundong.vo.search.HotGoodsSearchVo;
import com.zdata.yundong.vo.search.HotRiseSearchVo;

@Service("hotRiseService")
public class HotRiseServiceImpl implements HotRiseService {

	@Autowired
	private HotGoodsService hotGoodsService;
	
	@Autowired
	private HotRiseMapper hotRiseMapper;
	
	@Autowired
	private HotGoodsMapper hotGoodsMapper;
	
	@Autowired
	private TrendMapper trendMapper;
	
	Logger log = LoggerFactory.getLogger(HotRiseServiceImpl.class);
	
	/**
	 * 每天23点执行一次：0 0 23 * * ?
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	@Override
	public void updateInfo() {
		try {
			hotGoodsService.updateRise(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
			log.info("执行"+DateUtil.formatDate(new Date(), "yyyy-MM-dd")+"新上榜数据完毕");
		} catch (Exception e) {
			log.error("执行"+DateUtil.formatDate(new Date(), "yyyy-MM-dd")+"新上榜数据出错"+e);
			e.printStackTrace();
		}
	}

	@Override
	public RetResult<?> find(HotRiseSearchVo searchVo) throws Exception{
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==18637){
			searchVo.setCatId(null);
		}
		List<HotRise> list = hotRiseMapper.find(searchVo);
		//计算连续上榜天数
		for (HotRise hotRise : list) {
			//上榜时间
			String riseDate = DateUtil.formatDate(hotRise.getRiseDate(), "yyyy-MM-dd");
			String goodsName = hotRise.getGoodsName();
			HotGoodsSearchVo vo = new HotGoodsSearchVo(riseDate, goodsName);
			List<HotGoods> goods = hotGoodsMapper.findAfter(vo);
			List<HotGoods> result = combineGoods(goods);
			if (goods!=null&&goods.size()>0) {
				hotRise.setVisitorNum(goods.get(0).getVisitorNum());
			}
			hotRise.setTag(""+(result.size()+1));
		}
		int num = 0;
		Iterator<HotRise> iterator= list.iterator();
		while (iterator.hasNext()) {
			HotRise record = iterator.next();
			if (record!=null&&record.getVisitorNum()<500) {
				iterator.remove();
				num++;
			}
		};
		return RetResponse.makeOKRsp(list,list.size()-num);
	}

	@Override
	public RetResult<?> findLine(HotRiseSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==18637){
			searchVo.setCatId(null);
		}
		List<HotRise> list = hotRiseMapper.find(searchVo);
		DataViews views = new DataViews();
		final List<String> title = new ArrayList<String>();
		final List<Data> series = new ArrayList<Data>();
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		for (int i = 0; i < list.size(); i++) {
			HotRise record = list.get(i);
			List<HotGoods> surges = hotGoodsMapper.findByGoodsName(record.getGoodsName());
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
	
	private List<HotGoods> combineGoods(List<HotGoods> hotGoods){
		List<HotGoods> goodsList = new ArrayList<>();
	    for (HotGoods hotGoood : hotGoods) {
	        boolean flag = false;
	        for (HotGoods hotGoodsNew : goodsList) {
	            if (hotGoodsNew.getUpdateDate().equals(hotGoood.getUpdateDate())&&hotGoodsNew.getGoodsName().equals(hotGoood.getGoodsName())) {
	                flag = true;
	            }
	        }
	        if (!flag) {
	            goodsList.add(hotGoood);
	        }
	    }
		return goodsList;
	}
	
}

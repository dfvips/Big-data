package com.zdata.yundong.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdata.yundong.dao.GoodsCateMapper;
import com.zdata.yundong.dao.HotGoodsMapper;
import com.zdata.yundong.dao.HotRiseMapper;
import com.zdata.yundong.dao.TrendMapper;
import com.zdata.yundong.model.Data;
import com.zdata.yundong.model.DataInfo;
import com.zdata.yundong.model.DataViews;
import com.zdata.yundong.model.DataxAxis;
import com.zdata.yundong.model.DatayAxis;
import com.zdata.yundong.model.GoodsCate;
import com.zdata.yundong.model.HotGoods;
import com.zdata.yundong.model.HotRise;
import com.zdata.yundong.model.Trend;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotGoodsService;
import com.zdata.yundong.util.DateUtil;
import com.zdata.yundong.util.SimilarityUtil;
import com.zdata.yundong.util.StringUtil;
import com.zdata.yundong.vo.search.HotGoodsSearchVo;

@Service("hotGoodsService")
public class HotGoodsServiceImpl implements HotGoodsService {

	Logger log = LoggerFactory.getLogger(HotGoodsServiceImpl.class);
	
	@Autowired
	private HotGoodsMapper hotGoodsMapper;
	
	@Autowired
	private HotRiseMapper hotRiseMapper;
	
	@Autowired
	private TrendMapper trendMapper;
	
	@Autowired
	private GoodsCateMapper goodsCateMapper;
	
	@Override
	public RetResult<?> findByCatId(Integer catId) throws Exception {
		List<HotGoods> list = hotGoodsMapper.findByCatId(catId);
		if(list!=null&&list.size()>0){
			BigDecimal[] one = new BigDecimal[list.size()];//?????????
			BigDecimal[] two = new BigDecimal[list.size()];//?????????
			String[] data = new String[list.size()];
			Integer maxOne = new Integer(0);
			Integer maxTwo = new Integer(0);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getOrderNum()>maxOne){
					maxOne = list.get(i).getOrderNum();
				}
				if(list.get(i).getVisitorNum()>maxTwo){
					maxTwo = list.get(i).getVisitorNum();
				}
				one[i] = new BigDecimal(list.get(i).getOrderNum());
				two[i] = new BigDecimal(list.get(i).getVisitorNum());
				data[i] = list.get(i).getGoodsName();
			}
			final String[] legend = new String[]{"??????","??????"};
			DataViews model = new DataViews();
			model.setLegend(legend);
			final List<Data> datas = new ArrayList<Data>();
			Data oneData = new Data();//??????????????????
			oneData.setName("??????");
			oneData.setType("line");
			oneData.setxAxisIndex(1);
			oneData.setyAxisIndex(1);
			oneData.setData(one);
			datas.add(oneData);
			Data twoData = new Data();//??????????????????
			twoData.setName("??????");
			twoData.setType("bar");
			twoData.setxAxisIndex(1);
			twoData.setyAxisIndex(1);
			twoData.setData(two);
			datas.add(twoData);
			model.setSeries(datas);
			final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
			DataxAxis xAxisOne = new DataxAxis(data);
			xAxis.add(xAxisOne);
			model.setxAxis(xAxis);
			final List<DatayAxis> yAxis = new ArrayList<DatayAxis>();
			DatayAxis yAxisOne = new DatayAxis("value", "??????",0,maxOne);
			yAxis.add(yAxisOne);
			DatayAxis yAxisTwo = new DatayAxis("value", "??????",0,maxTwo);
			yAxis.add(yAxisTwo);
			model.setyAxis(yAxis);
			return RetResponse.makeOKRsp(model);
		}else{
			return RetResponse.makeOKRsp(null);
		}
	}

	@Override
	public RetResult<?> findInfoByGoodsName(String goodsName) throws Exception {
		List<HotGoods> list = hotGoodsMapper.findInfoByGoodsName(goodsName); 
		return RetResponse.makeOKRsp(list);
	}

	@Override
	public RetResult<?> findListByCatId(HotGoodsSearchVo searchVo) throws Exception {
		if (searchVo!=null&&searchVo.getCatId()==18637){
			searchVo.setCatId(null);
		}
		if (StringUtil.isNotEmpty(searchVo.getUpdateDate())&&searchVo.getUpdateDate().length()==23) {
			searchVo.setBeginDate(searchVo.getUpdateDate().substring(0, 10));
			searchVo.setEndDate(searchVo.getUpdateDate().substring(13));
			searchVo.setUpdateDate(null);
		}
		PageMethod.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotGoods> list = hotGoodsMapper.findListByCatId(searchVo);
		for (HotGoods hotGoods : list) {
			BigDecimal order = new BigDecimal(hotGoods.getOrderNum());
			BigDecimal visitor = new BigDecimal(hotGoods.getVisitorNum());
			//????????????
			BigDecimal result = order.divide(visitor, 2, BigDecimal.ROUND_HALF_UP);
			hotGoods.setTraning(result);
		}
		PageInfo<HotGoods> info = new PageInfo<HotGoods>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}
	
	
	
	
	@Override
	public RetResult<?> findPieByCatId(Integer catId) throws Exception {
		List<HotGoods> list = hotGoodsMapper.findByCatId(catId);
		if(list!=null&&list.size()>0){
			DataViews model = new DataViews();
			Data data = new Data();
			data.setName("????????????");
			data.setType("pie");
			final List<DataInfo> infos = new ArrayList<DataInfo>();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setGoodsName((list.size() - i)+":"+list.get(i).getGoodsName());
				DataInfo info = new DataInfo(list.get(i).getVisitorNum(), list.get(i).getGoodsName());
				infos.add(info);
			}
			data.setInfos(infos);
			final List<Data> datas = new ArrayList<Data>();
			datas.add(data);
			model.setSeries(datas);
			return RetResponse.makeOKRsp(model);
		}else{
			return RetResponse.makeOKRsp(null);
		}
	}

	@Override
	public RetResult<?> findPieCatId(Integer catId) throws Exception {
		/*
		searchVo.setCatId(catId);
		searchVo.setBeginDate("2020-08-07");
		searchVo.setEndDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));*/
		//??????????????????
		DataViews model = new DataViews();
		List<HotGoods> list = hotGoodsMapper.findByCatId(catId);
		// ??????lists
		Collections.reverse(list);
		if(list!=null&&list.size()>0){
			String[] legend = new String[list.size()];
			for (int i=0;i<list.size();i++) {
				legend[i] = list.get(i).getGoodsName();
			}
			model.setLegend(legend);
			final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
			Integer num = DateUtil.differentDays(DateUtil.formatString("2020-08-06", "yyyy-MM-dd"), new Date()); 
			String[] date = DateUtil.getDays(num);
			BigDecimal[] dp = new BigDecimal[num];
			for (int i = 0; i < date.length; i++) {
				Trend trend = trendMapper.selectByDate(date[i], catId);
				if (trend!=null) {
					dp[i] = new BigDecimal(trend.getVisitorNum());	
				}else{
					dp[i] = BigDecimal.ZERO;
				}
			}
			DataxAxis xAxisOne = new DataxAxis(date);
			xAxis.add(xAxisOne);
			model.setxAxis(xAxis);//x???
			final List<Data> datas = new ArrayList<Data>();
			for (int i=0;i<list.size();i++) {
				HotGoodsSearchVo searchVo = new HotGoodsSearchVo(catId, list.get(i).getGoodsName());
				Data data = new Data();
				data.setName(list.get(i).getGoodsName());
				data.setType("line");
				List<HotGoods> result = hotGoodsMapper.findPieCatId(searchVo);
				BigDecimal[] bigDecimals = new BigDecimal[date.length];
				for (int j = 0; j < result.size(); j++) {
					//????????????????????????????????????????????????
					for (int k = 0; k < date.length; k++) {
						if(date[k].equals(DateUtil.formatDate(result.get(j).getUpdateDate(), "yyyy-MM-dd"))){
							
							//?????????????????????
							BigDecimal fenzi = new BigDecimal(result.get(j).getVisitorNum());
							BigDecimal fenmu = dp[k];
							//????????????
							if(fenmu.compareTo(BigDecimal.ZERO)==0){
								bigDecimals[k] = BigDecimal.ZERO;
							}else{
								bigDecimals[k] = fenzi.divide(fenmu, 4, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
				}
				for (int j = 0; j < bigDecimals.length; j++) {
					if (bigDecimals[j]==null) {
						bigDecimals[j] = BigDecimal.ZERO;
					}
				}
				data.setData(bigDecimals);
				datas.add(data);
			}
			model.setSeries(datas);
		}
		
		return RetResponse.makeOKRsp(model);
	}

	@Override
	public void updateRise(String updateDate) throws Exception {
		log.info("???????????????"+updateDate+"?????????????????????");
		HotGoodsSearchVo searchVo = new HotGoodsSearchVo(updateDate);
		List<HotGoods> list = hotGoodsMapper.findListByCatId(searchVo);
		if (list!=null&&list.size()>0) {
			String[] goodsNames = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				goodsNames[i] = list.get(i).getGoodsName();
			}
			List<HotGoods> info = hotGoodsMapper.findCountByGoodsName(goodsNames,updateDate);
			for (HotGoods hotGoods : info) {
				if(hotGoods.getGoodsNameCount()==1){
					HotGoods goods = hotGoodsMapper.selectByGoodsName(hotGoods.getGoodsName(),updateDate);
					HotRise hotRise = new HotRise();
					hotRise.setGoodsName(goods.getGoodsName());
					hotRise.setHotGoodsId(goods.getId());
					hotRise.setVisitorNum(goods.getVisitorNum());
					hotRise.setOrderNum(goods.getOrderNum());
					hotRise.setRiseDate(goods.getUpdateDate());
					hotRise.setCatId(goods.getCatId());
					hotRise.setCatName(goods.getCatName());
					//????????????
					BigDecimal trans = divide(new BigDecimal(goods.getOrderNum()), new BigDecimal(goods.getVisitorNum()), 6);
					hotRise.setTranslation(trans.toString());
					HotGoodsSearchVo dpVo = new HotGoodsSearchVo(updateDate, goods.getCatId());
					//??????????????????
					
					List<HotGoods> dpNum = hotGoodsMapper.findByDate(dpVo);
					if(dpNum!=null&&dpNum.size()>0){
						//????????????????????????
						Trend trend = trendMapper.selectByDate(updateDate, goods.getCatId());
						if (trend!=null) {
							BigDecimal visitor = divide(new BigDecimal(goods.getVisitorNum()), new BigDecimal(trend.getVisitorNum()), 6);
							hotRise.setVisitorPercent(visitor.toString());
						}
						BigDecimal order = divide(new BigDecimal(goods.getOrderNum()), new BigDecimal(dpNum.get(0).getOrderNum()), 6);
						hotRise.setOrderPercent(order.toString());
					}
					HotRise pre = hotRiseMapper.selectByGoodsName(goods.getGoodsName(),updateDate);
					//?????????????????????????????????????????????????????????
					if(pre==null){
						hotRiseMapper.insert(hotRise);
					}
				}
			}
		}
		log.info("???????????????"+updateDate+"?????????????????????");
	}
	
	

	@Override
	public RetResult<?> findDetail(HotGoodsSearchVo searchVo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<HotGoods> search = hotGoodsMapper.findListByCatId(searchVo);
		List<HotGoods> list = combineGoods(search);
		//???????????????
		for (HotGoods hotGoods : list) {
			BigDecimal order = new BigDecimal(hotGoods.getOrderNum());
			BigDecimal visitor = new BigDecimal(hotGoods.getVisitorNum());
			//????????????
			BigDecimal result = order.divide(visitor, 6, BigDecimal.ROUND_HALF_UP);
			hotGoods.setTraning(result);
		}
		map.put("list", list);
		DataViews views = new DataViews();
		String[] legend = new String[]{"????????????","????????????","????????????????????????"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		String[] fkFeild = new String[list.size()];
		String[] ddFeild = new String[list.size()];
		String[] dpFeild = new String[list.size()];
		BigDecimal[] fkValue = new BigDecimal[list.size()];
		BigDecimal[] ddValue = new BigDecimal[list.size()];
		BigDecimal[] dpValue = new BigDecimal[list.size()];
		for (int i = 0; i < list.size(); i++) {
			fkFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			ddFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			dpFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			fkValue[i] = new BigDecimal(list.get(i).getVisitorNum());
			ddValue[i] = new BigDecimal(list.get(i).getOrderNum());
			//HotGoodsSearchVo dpVo = new HotGoodsSearchVo(DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd"), list.get(i).getCatId());
			//List<HotGoods> dpSum = hotGoodsMapper.findByDate(dpVo);
			Trend trend = trendMapper.selectByDate(DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd"), list.get(i).getCatId());
			if (trend!=null) {
				dpValue[i] = divide(new BigDecimal(list.get(i).getVisitorNum()), new BigDecimal(trend.getVisitorNum()), 6);
			}
		}
		DataxAxis fkXaxis = new DataxAxis();
		fkXaxis.setData(fkFeild);
		xAxis.add(fkXaxis);
		DataxAxis ddXaxis = new DataxAxis();
		ddXaxis.setData(ddFeild);
		xAxis.add(ddXaxis);
		DataxAxis dpXaxis = new DataxAxis();
		dpXaxis.setData(dpFeild);
		xAxis.add(dpXaxis);
		views.setxAxis(xAxis);
		//????????????
		final List<DatayAxis> yAxis = new ArrayList<DatayAxis>();
		DatayAxis yaxix = new DatayAxis();
		yaxix.setName("value");
		yAxis.add(yaxix);
		views.setyAxis(yAxis);
		//????????????
		final List<Data> series = new ArrayList<Data>();
		Data fkData = new Data();
		fkData.setName("????????????");
		fkData.setType("line");
		fkData.setData(fkValue);
		series.add(fkData);
		Data ddData = new Data();
		ddData.setName("????????????");
		ddData.setType("line");
		ddData.setData(ddValue);
		series.add(ddData);
		Data dpData = new Data();
		dpData.setName("????????????????????????");
		dpData.setType("line");
		dpData.setData(dpValue);
		series.add(dpData);
		views.setSeries(series);
		map.put("views", views);
		return RetResponse.makeOKRsp(map,list.size());
	}
	
	@Override
	public RetResult<?> findProgress(HotGoodsSearchVo searchVo) throws Exception {
		List<HotGoods> result = new ArrayList<HotGoods>(); 
		//????????????
		if (searchVo!=null&&searchVo.getCatId()==18637){
			List<GoodsCate> list = goodsCateMapper.find();
			for (int i = 0; i < list.size(); i++) {
				searchVo.setCatId(list.get(i).getCatId());
				result.addAll(getByCatId(searchVo));
			}
			Collections.sort(result, new Comparator<HotGoods>() {
			    @Override
				public int compare(HotGoods o1,HotGoods o2){
			    	return o1.getProcessNum()-o2.getProcessNum();
			    }
			});
		}else{
			result = getByCatId(searchVo);
		}
		return RetResponse.makeOKRsp(result, result.size());
	}
	
	@Override
	public RetResult<?> findHot(HotGoodsSearchVo searchVo) throws Exception {
		List<HotGoods> result = new ArrayList<HotGoods>();
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==18637){
			List<GoodsCate> cates = goodsCateMapper.find();
			for (int i = 0; i < cates.size(); i++) {
				searchVo.setCatId(cates.get(i).getCatId());
				result.addAll(getHotList(searchVo));
			}
		}else{
			result = getHotList(searchVo);
		}
		Iterator<HotGoods> iterator= result.iterator();
		while (iterator.hasNext()) {
			HotGoods hotGoods = iterator.next();
			HotGoodsSearchVo item = new HotGoodsSearchVo(hotGoods.getCatId(), DateUtil.formatDate(hotGoods.getUpdateDate(), "yyyy-MM-dd"), hotGoods.getGoodsName());
			List<HotGoods> list = hotGoodsMapper.findThree(item);
			int[] array = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i).getVisitorNum();
			}
			if (!isDecrease(array)) {
				iterator.remove();
			}
		}
		return RetResponse.makeOKRsp(result, result.size());
	}
	
	@Override
	public void updateRowNum(String updateDate) throws Exception {
		log.info("???????????????"+updateDate+"????????????????????????");
		List<GoodsCate> list = goodsCateMapper.find();
		for (int i = 0; i < list.size(); i++) {
			HotGoodsSearchVo searchVo = new HotGoodsSearchVo(updateDate, list.get(i).getCatId());
			List<HotGoods> result = hotGoodsMapper.findListByCatId(searchVo);
			//??????????????????
			Collections.sort(result, new Comparator<HotGoods>() {
				@Override
				public int compare(HotGoods o1, HotGoods o2) {
					return o2.getVisitorNum()-o1.getVisitorNum();
				}
			}); 
			for (int j = 0; j < result.size(); j++) {
				HotGoods record = result.get(j);
				record.setRowNum(j+1);
				hotGoodsMapper.updateByPrimaryKeySelective(record);
			}
		}
		log.info("???????????????"+updateDate+"????????????????????????");
	}

	@Override
	public RetResult<?> findRelated(HotGoodsSearchVo searchVo) throws Exception {
		String goodsName = searchVo.getGoodsName();
		searchVo.setGoodsName(null);
		List<HotGoods> list = hotGoodsMapper.findListByCatId(searchVo);
		List<HotGoods> goods = new ArrayList<HotGoods>();
		for (int i = 0; i < list.size(); i++) {
			float related = SimilarityUtil.getSimilarity(goodsName, list.get(i).getGoodsName());
			if (related>0.65) {
				list.get(i).setRelated(related);
				goods.add(list.get(i));
			}
		}
		return RetResponse.makeOKRsp(goods, goods.size());
	}
	
	 /**
	  * ??????????????????
	 ??*??@param:????????????????@param array
	 ??*??@param:????????????????@return ?? ??
	 ??*??@author:?? ?? ?? ?????????????????
	 ??*??@Date: ?? ?? ?? ?? 2020???9???24???????????8:46:48??
	  */
    private boolean isDecrease(int[] array) {
        if (array.length<=1) {
            return true;
        }
        if (array[0]>array[1]) {
            return isDecrease(Arrays.copyOfRange(array, 1, array.length));
        }
        return false;
    }
	
	private List<HotGoods> getHotList(HotGoodsSearchVo searchVo) {
		List<HotGoods> list = hotGoodsMapper.findHot(searchVo);
		//????????????????????????500??????
		Iterator<HotGoods> iterator= list.iterator();
		while (iterator.hasNext()) {
			HotGoods record = iterator.next();
			if (record==null) {
				iterator.remove();
			}else{
				if (record.getVisitorNum()!=null&&record.getVisitorNum()<500){
					iterator.remove();
				}
			}
		}
		return list;
	}
	
	private List<HotGoods> getByCatId(HotGoodsSearchVo searchVo){
		//???????????????
		List<HotGoods> today = hotGoodsMapper.findProgress(searchVo);
		List<HotGoods> yesterday = hotGoodsMapper.findProgress(getSearchVo(searchVo));
		for (int i = 0; i < today.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < yesterday.size(); j++) {
				//?????????????????????????????????
				if (today.get(i).getGoodsName().equals(yesterday.get(j).getGoodsName())) {
					//???
					Integer processNum =  i - j;
					today.get(i).setProcessNum(processNum);
					flag = false;
				} else {
					if (flag) {
						Integer processNum =  i - 50;
						today.get(i).setProcessNum(processNum);
					}
				}
			}
		}
		//????????????????????????
		Iterator<HotGoods> iterator= today.iterator();
		while (iterator.hasNext()) {
			HotGoods record = iterator.next();
			if (record==null) {
				iterator.remove();
			}else{
				if (record.getProcessNum()!=null&&record.getProcessNum()>=0) {
					iterator.remove();
				}else{
					if (record.getVisitorNum()!=null&&record.getVisitorNum()<500){
						iterator.remove();
					}
				}
				
			}
			
		}
		Collections.sort(today, new Comparator<HotGoods>() {
		    @Override
			public int compare(HotGoods o1,HotGoods o2){
		    	return o1.getProcessNum()-o2.getProcessNum();
		    }
		});
		return today;
	}
	
	private HotGoodsSearchVo getSearchVo(HotGoodsSearchVo searchVo) {
		HotGoodsSearchVo result = new HotGoodsSearchVo();
		String updateDate = searchVo.getUpdateDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.formatString(updateDate, "yyyy-MM-dd"));
		calendar.add(Calendar.DATE,-2);
		result.setUpdateDate(DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
		result.setCatId(searchVo.getCatId());
		return result;
	}
	
	/**
	 * ????????????
	??*??@param:????????????????@param hotGoods
	??*??@param:????????????????@return ?? ??
	??*??@author:?? ?? ?? ?????????????????
	??*??@Date: ?? ?? ?? ?? 2020???8???20???????????6:43:03
	 */
	private List<HotGoods> combineGoods (List<HotGoods> hotGoods){
		List<HotGoods> goodsList = new ArrayList<>();
	    for (HotGoods hotGoood : hotGoods) {
	        boolean flag = false;
	        int num = 1;
	        for (HotGoods hotGoodsNew : goodsList) {
	            if (hotGoodsNew.getUpdateDate().equals(hotGoood.getUpdateDate())&&hotGoodsNew.getGoodsName().equals(hotGoood.getGoodsName())) {
	                num++;
	            	hotGoodsNew.setOrderNum(hotGoood.getOrderNum()+hotGoodsNew.getOrderNum());
	                hotGoodsNew.setVisitorNum(hotGoood.getVisitorNum()+hotGoodsNew.getVisitorNum());
	                hotGoodsNew.setNum(num);
	                flag = true;
	            } else{
	            	hotGoood.setNum(num);
	            }
	        }
	        if (!flag) {
	            goodsList.add(hotGoood);
	        }
	    }
		return goodsList;
	}
	
	/**
	 * ???????????????num????????????????????????
	??*??@param:????????????????@param fenzi
	??*??@param:????????????????@param fenmu
	??*??@param:????????????????@param num
	??*??@param:????????????????@return ?? ??
	??*??@author:?? ?? ?? ?????????????????
	??*??@Date: ?? ?? ?? ?? 2020???8???19???????????5:10:54??
	 */
	private BigDecimal divide(BigDecimal fenzi,BigDecimal fenmu,Integer num){
		BigDecimal result = fenzi.divide(fenmu, num, BigDecimal.ROUND_HALF_UP);
		return result;
	}

}

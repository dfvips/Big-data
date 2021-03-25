package com.zdata.yundong.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdata.yundong.dao.HotWordsMapper;
import com.zdata.yundong.dao.HotWordsRiseMapper;
import com.zdata.yundong.dao.YoungTrendMapper;
import com.zdata.yundong.dao.YoungWordMapper;
import com.zdata.yundong.model.Data;
import com.zdata.yundong.model.DataViews;
import com.zdata.yundong.model.DataxAxis;
import com.zdata.yundong.model.HotWords;
import com.zdata.yundong.model.HotWordsRise;
import com.zdata.yundong.model.YoungTrend;
import com.zdata.yundong.model.YoungWord;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsService;
import com.zdata.yundong.util.DateUtil;
import com.zdata.yundong.util.SimilarityUtil;
import com.zdata.yundong.util.StringUtil;
import com.zdata.yundong.vo.search.HotWordsSearchVo;

@Service("hotWordsService")
public class HotWordsServiceImpl implements HotWordsService {

	Logger log = LoggerFactory.getLogger(HotWordsServiceImpl.class);
	
	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Autowired
	private HotWordsRiseMapper hotWordsRiseMapper;
	
	@Autowired
	private YoungWordMapper youngWordMapper;
	
	@Autowired 
	private YoungTrendMapper youngTrendMapper;
	
	@Override
	public RetResult<?> find(HotWordsSearchVo searchVo) throws Exception {
		if (StringUtil.isNotEmpty(searchVo.getUpdateDate())) {
			searchVo.setBeginDate(searchVo.getUpdateDate().substring(0, 10));
			searchVo.setEndDate(searchVo.getUpdateDate().substring(13));
		}
		PageMethod.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotWords> list = hotWordsMapper.findPage(searchVo);
		PageInfo<HotWords> info = new PageInfo<HotWords>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findByCatId(Integer catId) throws Exception {
		List<HotWords> list = hotWordsMapper.findByCatId(catId);
		return RetResponse.makeOKRsp(list);
	}

	@Override
	public RetResult<?> findListByCatId(HotWordsSearchVo searchVo) throws Exception {
		/**
		 * 按照子类目Id查一天数据，拼多多那边给了一天100条数据
		 */
		List<HotWords> list = hotWordsMapper.findListByCatId(searchVo);
		HotWords hotWords = hotWordsMapper.selectByDate(searchVo);
		//计算访客占比，点击占比
		for (int i = 0; i < list.size(); i++) {
			BigDecimal fkFenzi = new BigDecimal(list.get(i).getPv());
			BigDecimal fkFenmu = new BigDecimal(hotWords.getPv()); 
			BigDecimal fkResult = divide(fkFenzi,fkFenmu,6);
			list.get(i).setPvPercent(fkResult);
			BigDecimal djFenzi = new BigDecimal(list.get(i).getClickNum());
			BigDecimal djFenmu = new BigDecimal(hotWords.getClickNum());
			BigDecimal djResult = divide(djFenzi,djFenmu,6);
			list.get(i).setClickPercent(djResult);
		}
		return RetResponse.makeOKRsp(list,list.size());
	}
	
	

	@Override
	public RetResult<?> findDetail(HotWordsSearchVo searchVo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<HotWords> list = hotWordsMapper.findListByCatId(searchVo);
		for (int i = 0; i < list.size(); i++) {
			//上榜时间
			String riseDate = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			HotWordsSearchVo vo = new HotWordsSearchVo(riseDate, list.get(i).getCatId());
			HotWords dpInfo  = hotWordsMapper.selectByDate(vo);
			if (dpInfo!=null) {
				BigDecimal pvPercent = divide(new BigDecimal(list.get(i).getPv()), new BigDecimal(dpInfo.getPv()), 6);
				list.get(i).setPvPercent(pvPercent);
				BigDecimal clickNumPercent = divide(new BigDecimal(list.get(i).getClickNum()), new BigDecimal(dpInfo.getClickNum()), 6);
				list.get(i).setClickPercent(clickNumPercent);
			}
			list.get(i).setWordCount(1);
		}
		map.put("list", list);
		DataViews views = new DataViews();
		String[] legend = new String[]{"搜索热度占大盘线图","点击热度线图","竞争强度线图"};
		views.setLegend(legend);
		final List<DataxAxis> xAxis = new ArrayList<DataxAxis>();
		String[] ssFeild = new String[list.size()];
		String[] djFeild = new String[list.size()];
		String[] jzFeild = new String[list.size()];
		BigDecimal[] ssValue = new BigDecimal[list.size()];
		BigDecimal[] djValue = new BigDecimal[list.size()];
		BigDecimal[] jzValue = new BigDecimal[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ssFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			djFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			jzFeild[i] = DateUtil.formatDate(list.get(i).getUpdateDate(), "yyyy-MM-dd");
			djValue[i] = new BigDecimal(list.get(i).getClickNum()); 
			jzValue[i] = new BigDecimal(list.get(i).getCompeteValue()); 
			HotWordsSearchVo vo = new HotWordsSearchVo(ssFeild[i], list.get(i).getCatId());
			HotWords words = hotWordsMapper.selectByDate(vo);
			if (words!=null) {
				ssValue[i] = divide(new BigDecimal(list.get(i).getPv()), new BigDecimal(words.getPv()), 6);
			}
		}
		DataxAxis ssXaxis = new DataxAxis();
		ssXaxis.setData(ssFeild);
		xAxis.add(ssXaxis);
		DataxAxis djXaxix = new DataxAxis();
		djXaxix.setData(djFeild);
		xAxis.add(djXaxix);
		DataxAxis jzXaxix = new DataxAxis();
		jzXaxix.setData(jzFeild);
		xAxis.add(jzXaxix);
		views.setxAxis(xAxis);
		final List<Data> series = new ArrayList<Data>();
		Data ssData = new Data();
		ssData.setName("搜索热度占大盘线图");
		ssData.setType("line");
		ssData.setData(ssValue);
		series.add(ssData);
		Data djData = new Data();
		djData.setName("点击热度线图");
		djData.setType("line");
		series.add(djData);
		djData.setData(djValue);
		Data jzData = new Data();
		jzData.setName("竞争强度线图");
		jzData.setType("line");
		jzData.setData(jzValue);
		series.add(jzData);
		views.setSeries(series);
		map.put("views", views);
		return RetResponse.makeOKRsp(map,list.size());
	}

	@Override
	public void updateRise(String updateDate) throws Exception {
		log.info("开始执行【"+updateDate+"】新热门词数据");
		HotWordsSearchVo searchVo = new HotWordsSearchVo(updateDate);
		List<HotWords> list = hotWordsMapper.findListByCatId(searchVo);
		if (list!=null&&list.size()>0) {
			String[] words = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				words[i] = list.get(i).getWord();
			}
			List<HotWords> info = hotWordsMapper.findCountByWords(words, updateDate);
			for (HotWords hotWords : info) {
				if(hotWords.getWordCount()==1){
					HotWords record = hotWordsMapper.selectByWord(hotWords.getWord(),updateDate);
					HotWordsRise rise = new HotWordsRise();
					rise.setWord(record.getWord());
					rise.setHotWordsId(record.getId());
					rise.setPv(record.getPv());
					rise.setClickNum(record.getClickNum());
					rise.setCtr(record.getCtr());
					rise.setCvr(record.getCvr());
					rise.setCompeteValue(record.getCompeteValue());
					rise.setImprAvgBid(record.getImprAvgBid());
					rise.setCatId(record.getCatId());
					rise.setCatName(record.getCatName());
					rise.setRiseDate(DateUtil.formatString(updateDate, "yyyy-MM-dd"));
					HotWordsSearchVo vo = new HotWordsSearchVo(updateDate, record.getCatId());
					HotWords dpInfo  = hotWordsMapper.selectByDate(vo);
					if (dpInfo!=null) {
						BigDecimal pvPercent = divide(new BigDecimal(record.getPv()), new BigDecimal(dpInfo.getPv()), 6);
						rise.setPvPercent(pvPercent.toString());
						BigDecimal clickNumPercent = divide(new BigDecimal(record.getClickNum()), new BigDecimal(dpInfo.getClickNum()), 6);
						rise.setClickNumPercent(clickNumPercent.toString());
					}
					HotWordsRise wordsRise = hotWordsRiseMapper.selectByWord(record.getWord());
					if (wordsRise==null) {
						hotWordsRiseMapper.insert(rise);
					}
				}
			}
		}
		log.info("结束执行【"+updateDate+"】新热门词数据");
	}

	@Override
	public void findYoung(Integer catId) throws Exception {
		log.info("计算蓝海词开始！");
		List<YoungWord> list = youngWordMapper.findByState(0);
		for (int i = 0; i < list.size(); i++) {
			YoungWord word = list.get(i);
			List<YoungTrend> info = youngTrendMapper.findByWordId(word.getId());
			if (info!=null&&info.size()>0) {
				if(info.size()==1){
					word.setState(1);
					word.setYoungValue(info.get(0).getPv().toString());
					youngWordMapper.updateByPrimaryKey(word);
				}else{
					if(info.size()>7){
						//截取最后一个星期数据
						List<YoungTrend> youngTrends = info.subList(info.size()-7, info.size());
						Integer max = getMax(youngTrends);
						Integer min = getMin(youngTrends);
						log.info("计算【"+list.get(i).getWord()+"】倾斜程度指标 : 最大值("+max+"),最小值:("+min+"),跨度：("+youngTrends.size()+")");
						//计算结果
						BigDecimal result = divide(new BigDecimal(max-min), new BigDecimal(youngTrends.size()), 6);
						log.info("计算【"+list.get(i).getWord()+"】倾斜程度结果："+result);
						word.setState(1);
						word.setYoungValue(result.toString());
						youngWordMapper.updateByPrimaryKey(word);
					}
				}
				
			}
		}
		//执行完毕后删除catId下youngValue为null
		youngTrendMapper.deleteByCatId(catId);
		youngWordMapper.deleteByCatId(catId);
		log.info("计算蓝海词结束！");
	}
	
	/**
	 * 获取最大值
	 * @param:        @param youngTrends
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月26日 下午6:52:10 
	 */
	private Integer getMax(List<YoungTrend> youngTrends){
		Integer max = youngTrends.get(0).getPv();
		for (int i = 0; i < youngTrends.size(); i++) {
			if(youngTrends.get(i).getPv()>max){
				max = youngTrends.get(i).getPv();
			}
		}
		return max;
	}
	
	private Integer getMin(List<YoungTrend> youngTrends){
		Integer min = youngTrends.get(0).getPv();
		for (int i = 0; i < youngTrends.size(); i++) {
			if(youngTrends.get(i).getPv()<min){
				min = youngTrends.get(i).getPv();
			}
		}
		return min;
	}
	
	private BigDecimal divide(BigDecimal fenzi,BigDecimal fenmu,Integer num){
		BigDecimal result = fenzi.divide(fenmu, num, BigDecimal.ROUND_HALF_UP);
		return result;
	}

	@Override
	public RetResult<?> findInfo(HotWordsSearchVo searchVo) throws Exception {
		PageMethod.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotWords> list = hotWordsMapper.findPage(searchVo);
		PageInfo<HotWords> info = new PageInfo<HotWords>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> findSame(HotWordsSearchVo searchVo) throws Exception {
		String word = searchVo.getWord();
		searchVo.setWord(null);
		List<HotWords> list = hotWordsMapper.findPage(searchVo);
		List<HotWords> words = new ArrayList<HotWords>();
		for (int i = 0; i < list.size(); i++) {
			float same = SimilarityUtil.getSimilarity(word, list.get(i).getWord());
			if (same>0.5) {
				list.get(i).setSame(same);
				words.add(list.get(i));
			}
		}
		return RetResponse.makeOKRsp(words, words.size());
	}
}

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
		 * ???????????????Id?????????????????????????????????????????????100?????????
		 */
		List<HotWords> list = hotWordsMapper.findListByCatId(searchVo);
		HotWords hotWords = hotWordsMapper.selectByDate(searchVo);
		//?????????????????????????????????
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
			//????????????
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
		String[] legend = new String[]{"???????????????????????????","??????????????????","??????????????????"};
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
		ssData.setName("???????????????????????????");
		ssData.setType("line");
		ssData.setData(ssValue);
		series.add(ssData);
		Data djData = new Data();
		djData.setName("??????????????????");
		djData.setType("line");
		series.add(djData);
		djData.setData(djValue);
		Data jzData = new Data();
		jzData.setName("??????????????????");
		jzData.setType("line");
		jzData.setData(jzValue);
		series.add(jzData);
		views.setSeries(series);
		map.put("views", views);
		return RetResponse.makeOKRsp(map,list.size());
	}

	@Override
	public void updateRise(String updateDate) throws Exception {
		log.info("???????????????"+updateDate+"?????????????????????");
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
		log.info("???????????????"+updateDate+"?????????????????????");
	}

	@Override
	public void findYoung(Integer catId) throws Exception {
		log.info("????????????????????????");
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
						//??????????????????????????????
						List<YoungTrend> youngTrends = info.subList(info.size()-7, info.size());
						Integer max = getMax(youngTrends);
						Integer min = getMin(youngTrends);
						log.info("?????????"+list.get(i).getWord()+"????????????????????? : ?????????("+max+"),?????????:("+min+"),?????????("+youngTrends.size()+")");
						//????????????
						BigDecimal result = divide(new BigDecimal(max-min), new BigDecimal(youngTrends.size()), 6);
						log.info("?????????"+list.get(i).getWord()+"????????????????????????"+result);
						word.setState(1);
						word.setYoungValue(result.toString());
						youngWordMapper.updateByPrimaryKey(word);
					}
				}
				
			}
		}
		//?????????????????????catId???youngValue???null
		youngTrendMapper.deleteByCatId(catId);
		youngWordMapper.deleteByCatId(catId);
		log.info("????????????????????????");
	}
	
	/**
	 * ???????????????
	??*??@param:????????????????@param youngTrends
	??*??@param:????????????????@return ?? ??
	??*??@author:?? ?? ?? ?????????????????
	??*??@Date: ?? ?? ?? ?? 2020???8???26???????????6:52:10??
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

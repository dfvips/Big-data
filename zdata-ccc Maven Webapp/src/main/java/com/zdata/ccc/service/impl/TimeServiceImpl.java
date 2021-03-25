package com.zdata.ccc.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.zdata.ccc.dao.HotWordsMapper;
import com.zdata.ccc.dao.YoungWordMapper;
import com.zdata.ccc.model.HotWords;
import com.zdata.ccc.model.YoungWord;
import com.zdata.ccc.service.GroupRiseService;
import com.zdata.ccc.service.HotGoodsService;
import com.zdata.ccc.service.HotWordsService;
import com.zdata.ccc.service.TimeService;
import com.zdata.ccc.util.DateUtil;

@Service("timeService")
public class TimeServiceImpl implements TimeService {

	Logger log = LoggerFactory.getLogger(TimeServiceImpl.class);
	
	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Autowired
	private YoungWordMapper youngWordMapper;
	
	@Autowired
	private GroupRiseService groupRiseService;
	
	@Autowired
	private HotGoodsService hotGoodsService;
	
	@Autowired
	private HotWordsService hotWordsService;
	
	/**
	 * 每天中午12点触发
	 */
	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void updateYoungWord() throws Exception {
		/**
		 * catId:18652洁面
		 * catId:18671面膜
		 * catId:18720身体乳/霜
		 * catId:18715沐浴露
		 * catId:18718身体护理套装
		 */
		insertWord(18652);
		insertWord(18671);
		insertWord(18720);
		insertWord(18715);
		insertWord(18718);
	}
	
	/**
	 * 每天下午6点触发
	 */
	@Scheduled(cron = "0 0 18 * * ?")
	@Override
	public void updateRise() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		hotGoodsService.updateRowNum(DateUtil.formatDate(date, "yyyy-MM-dd"));
		groupRiseService.updateRise(DateUtil.formatDate(date, "yyyy-MM-dd"));
		hotGoodsService.updateRise(DateUtil.formatDate(date, "yyyy-MM-dd"));
		hotWordsService.updateRise(DateUtil.formatDate(date, "yyyy-MM-dd"));
	}

	private void insertWord(Integer catId){
		List<HotWords> list = hotWordsMapper.findYoungByCatId(catId);
		for (HotWords hotWords : list) {
			//获取关键词的增长数据
			YoungWord record = new YoungWord();
			record.setState(0);
			record.setCatId(catId);
			record.setWord(hotWords.getWord());
			youngWordMapper.insert(record);
		}
		log.info("添加蓝海词完毕！catId:"+catId);
	}

}

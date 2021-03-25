package com.zdata.alibaba.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zdata.alibaba.service.HotRiseService;
import com.zdata.alibaba.service.TimeService;
import com.zdata.alibaba.util.DateUtil;

@Service("timeService")
public class TimeServiceImpl implements TimeService {

	@Autowired
	private HotRiseService hotRiseService;
	
	@Scheduled(cron = "0 0 18 * * ?")
	@Override
	public void updateRise() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		hotRiseService.updateRise(DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
	}

}

package com.zdata.sycm.service.impl;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zdata.sycm.controller.CateController;
import com.zdata.sycm.service.TimeService;
import com.zdata.sycm.util.DateUtil;


@Service("timeService")
public class TimeServiceImpl implements TimeService {

	@Autowired
	private CateController cateController;
	
	private static boolean Flag = false;//是否发送短信
	
	private static String phone = "15158832972";
	
	@Scheduled(cron = " */59 * * * * *")
	@Override
	public void gj() {
		if (Flag) {
			//String url = "http://www.szhjerp.com/xtPushtask/directSend?xphSenduiid=1&xphSendname=超级管理员&xphRecuiname=测试&xphRecuiphone=13129724052&xphReccontent=哈哈哈";
	    	String url = "https://api-service.chanmama.com/v1/access/sendLoginCode";
	    	//String url1 = "http://www.szhjerp.com/sysfiles/6d-5843-424e-af34-05aa46c961de.mp4";
	    	Document doc = null;
			Connection connection = Jsoup.connect(url);
			//connection = connection.header("Cookie", "ui_token=cd0b1e2567fa4ce381b4fa8c0c1f12fe");
			connection = connection.header("x-forwarded-for", "127.0.0.1");
			connection = connection.requestBody("{\"appId\":10000,\"username\":\""+phone+"\"}");
			try {
				doc = connection
						.userAgent(
								"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
						.timeout(50000).ignoreContentType(true).timeout(50000).ignoreContentType(true).post();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Scheduled(cron = " */59 * * * * *")
	@Override
	public void gj2() {
		if (Flag) {
	    	String url = "https://login.taosj.com/user/identifycode?mobile="+phone+"&template=4&countryCode=86";
	    	Document doc = null;
			Connection connection = Jsoup.connect(url);
			try {
				doc = connection
						.userAgent(
								"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
						.timeout(50000).ignoreContentType(true).timeout(50000).ignoreContentType(true).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

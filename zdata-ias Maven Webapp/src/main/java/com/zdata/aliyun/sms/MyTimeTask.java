package com.zdata.aliyun.sms;

import java.util.TimerTask;

import com.zdata.util.RandomUtils;

public class MyTimeTask extends TimerTask {

	@Override
	public void run() {
		AliyunSms aliyunSms = new AliyunSms();
		aliyunSms.setMobile("19155044448");
		aliyunSms.setTemplateCode("SMS_182865864");
		aliyunSms.setTemplateParam("{\"code\":\"" + RandomUtils.generate(2, 4) + "\"}");
		try {
			SendSmsResponse response = AliyunSmsApi.sendSms(aliyunSms);
		    System.out.println("短信接口返回的数据----------------");
		    System.out.println("Code=" + response.getCode());
		    System.out.println("Message=" + response.getMessage());
		    System.out.println("RequestId=" + response.getRequestId());
		    System.out.println("BizId=" + response.getBizId());
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}

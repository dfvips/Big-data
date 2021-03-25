package com.zdata.aliyun.sms;

import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import com.zdata.util.RandomUtils;


public class AliyunSmsApi {

	private static final String product = "Dysmsapi";
	private static final String domain = "dysmsapi.aliyuncs.com";
	private static final String accessKeyId = "LTAI4Fuw3NTVE7uUhHAo33UR";
	private static final String accessKeySecret = "9ipgLJwIKPa9gtjTqN12G04lYzsJXg";

	public static void main(String[] args) throws ClientException {
		/*
		 * DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
		 * accessKeyId, accessKeySecret); IAcsClient client = new
		 * DefaultAcsClient(profile);
		 * 
		 * CommonRequest request = new CommonRequest();
		 * request.setMethod(MethodType.POST); request.setDomain(domain);
		 * request.setVersion("2017-05-25"); request.setAction("SendSms");
		 * request.putQueryParameter("RegionId", "cn-hangzhou");
		 * request.putQueryParameter("PhoneNumbers", "13129724052");
		 * request.putQueryParameter("SignName", "智库网络");
		 * request.putQueryParameter("TemplateCode", "SMS_182865861");
		 * request.putQueryParameter("TemplateParam", "{\"code\":\"2015\"}");
		 * try { CommonResponse response = client.getCommonResponse(request);
		 * System.out.println(response.getData()); } catch (ServerException e) {
		 * e.printStackTrace(); } catch (ClientException e) {
		 * e.printStackTrace(); }
		 */
		Timer timer = new Timer();
		timer.schedule(new MyTimeTask(), 2000L,10000L);
		/*AliyunSms aliyunSms = new AliyunSms();
		aliyunSms.setMobile("19155044448");
		aliyunSms.setTemplateCode("SMS_182865864");
		aliyunSms.setTemplateParam("{\"code\":\"" + RandomUtils.generate(2, 4) + "\"}");
	    SendSmsResponse response = sendSms(aliyunSms);
	    System.out.println("短信接口返回的数据----------------");
	    System.out.println("Code=" + response.getCode());
	    System.out.println("Message=" + response.getMessage());
	    System.out.println("RequestId=" + response.getRequestId());
	    System.out.println("BizId=" + response.getBizId());*/
	}

	@SuppressWarnings("deprecation")
	public static SendSmsResponse sendSms(AliyunSms aliyunSms)
			throws ClientException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId, accessKeySecret);
	    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		request.setPhoneNumbers(aliyunSms.getMobile());
		request.setSignName("智库网络");
		request.setTemplateCode(aliyunSms.getTemplateCode());
		request.setTemplateParam(aliyunSms.getTemplateParam());
		SendSmsResponse sendSmsResponse = (SendSmsResponse) acsClient.getAcsResponse(request);
		return sendSmsResponse;
	}

}

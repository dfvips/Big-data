package com.zdata.sycm.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class SendSmsUtil {
	public static String APP_ID = "111888970000258082";
	public static String APP_SECRET = "4cbee968f9eae623bd5430284db53f7a";
	public static String ACCESS_TOKEN = null;
	public static String TEMPLATE_ID = "91551894";//参数 param1 param2
	public static Gson gson = new Gson();
	
	//操作成功
	public static final String SUCCESS = "0";
	
	
	/**
	 * 
	 * 模板短信发送
	 * 
	 * @param acceptor_tel 接收手机号
	 * @param sendContent 待发送内容
	 * @return
	 * @throws Exception
	 */
	public static String sendSms(String acceptor_tel,String sendContent) throws Exception{
		String result = null;
		if(isPhone(acceptor_tel) && sendContent != null){
			if(ACCESS_TOKEN == null){
				ACCESS_TOKEN = getAccessToken();
			}
			//验证是否为手机号
			String content1=sendContent.substring(0,sendContent.length()/2);
			String content2=sendContent.substring(sendContent.length()/2);
			Map<String, String> templateMap = new HashMap<>();
			templateMap.put("param1", content1);
			templateMap.put("param2", content2);
			String template_param = gson.toJson(templateMap);
			String time = TimeUtil.getNowTime();
			Map<String, String> params = new HashMap<>();
			params.put("app_id", APP_ID);
			params.put("access_token", ACCESS_TOKEN);
			params.put("acceptor_tel", acceptor_tel);
			params.put("template_id", TEMPLATE_ID);
			params.put("template_param", template_param);
			params.put("timestamp", URLEncoder.encode(time,"utf-8"));
			String param = SmsSignUtil.getLinkString(params);
			String resultStr = HttpUtil.httpPost(SmsRequestUrl.Send_Sms.getValue(), param);
			SmsResponse smsResponse = gson.fromJson(resultStr, SmsResponse.class);
			result = smsResponse.getRes_code();
		}
		return result;
	}
	
	/**
	 * 
	 * 获取AccessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken() throws Exception{
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		params.put("app_id", APP_ID);
		params.put("app_secret", APP_SECRET);
//		params.put("state", "Hj");
//		params.put("scope", "");
		String param = SmsSignUtil.getLinkString(params);
		String resultStr = HttpUtil.sendPost(SmsRequestUrl.Access_Token.getValue(), param);
		SmsResponse smsTokenResponse = gson.fromJson(resultStr, SmsResponse.class);
		return smsTokenResponse.getAccess_token();
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param acceptor_tel
	 * @return
	 */
	public static boolean isPhone(String acceptor_tel){
		if(acceptor_tel == null || acceptor_tel.trim().equals("")){
			return false;
		}
		Pattern pattern = Pattern.compile("^([1][3,4,5,6,7,8,9])\\d{9}$");
		Matcher isPhoneNum = pattern.matcher(acceptor_tel);
		return isPhoneNum.matches();
	}
	
	public static void main(String[] args) {
		String acceptor_tel = "13129724052";
		try {
//			String template_param = SmsSignUtil.getLinkString(params);
			String template_param = "尊敬的温亮先生/女生，你已经有23条待审批还没处理，为避免耽误工作，请及时登录http://www.szhjerp.com 审批相关流程。";
			String sendSmsResult = sendSms(acceptor_tel, template_param);
			System.out.println("sendSmsResult:"+sendSmsResult);
			//sendSmsResult:{"res_code" : 1,"res_message" : "checksum of sign field empty"}
			//sendSmsResult:{"res_code":0,"res_message":"Success","idertifier":"90610424113816407687"}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

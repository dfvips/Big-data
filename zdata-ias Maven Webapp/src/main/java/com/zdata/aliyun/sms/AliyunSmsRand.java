package com.zdata.aliyun.sms;

import com.zdata.util.RandomUtils;
import com.zdata.util.RedisUtil;

/**
 * 检查验证码
 * @author Administrator
 *
 */
public class AliyunSmsRand {
	
	public static boolean sendRand(String phone,RedisTemplate<String, Object> redisTemplate){
		boolean flag = false;
		try {
			String rand=RandomUtils.generate(2, 4);
			System.out.println("验证码："+rand);
			AliyunSms aliyunSms = new AliyunSms();
			aliyunSms.setMobile(phone);
			aliyunSms.setTemplateCode("SMS_182865861");
			aliyunSms.setTemplateParam("{\"code\":\"" + rand + "\"}");
			AliyunSmsApi.sendSms(aliyunSms);
			RedisUtil redisUtil=new RedisUtil(redisTemplate);
			//缓存存放数据
			redisUtil.set(generateRandKey(phone), rand, 900L);
			String code=(String) redisUtil.get(generateRandKey(phone));
			System.out.println("code:"+code);
			flag=true;
		} catch (ClientException e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * redis缓存获取手机验证码
	 * @param rand
	 * @param phone
	 * @return
	 */
	public static String checkRand(String rand, String phone,
			RedisTemplate<String, Object> redisTemplate) {
		/**
		 * 手机验证码为0000
		 */
		if ("0000".equals(rand)) {
			return "0";
		}
		RedisUtil redisUtil=new RedisUtil(redisTemplate);
		//缓存获取验证码
		String msgRand = (String) redisUtil.get(generateRandKey(phone));
	    if(StringUtils.isBlank(msgRand)){
	    	return "1";
	    }else if((!msgRand.equals(rand)) && (!"0000".equals(rand))){
	    	return "2";
	    }else {
	    	return "0";
	    }
	}
	
	private static String generateRandKey(String phone) {
		StringBuffer sb = new StringBuffer();
		sb.append("checkcode_");
		sb.append(phone);
		return sb.toString();
	}
	
}

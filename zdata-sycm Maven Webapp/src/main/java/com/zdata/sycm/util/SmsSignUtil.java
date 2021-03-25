package com.zdata.sycm.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.util.Base64;

import io.netty.handler.codec.base64.Base64Encoder;

/**
 * 
 * 短信发送签名工具类
 * 
 * @author huangzhuozhen
 * @Time 2019年4月23日 下午5:15:32 
 */
public class SmsSignUtil {
	
	private static final String ALGORITHM = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	
	private static final String valueConnect = "=";//参数值连接符
	private static final String keyConnect = "&";//参数值连接符
	
	public static String getSignStr(Map<String, String> params,String secret) throws UnsupportedEncodingException{
		String linkString = getLinkString(params);
		String hmacSignStr = getHmacSignStr(secret,linkString);
//		params.put("Signature", hmacSignStr);
		return hmacSignStr;
	}
	
	/**
	 * 
	 * 将key和value进行字典排序并编码过的字符串
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getLinkString(Map<String, String> params) throws UnsupportedEncodingException {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder result = new StringBuilder();
		for (String key : keys) {//遍历拼接属性
//			result.append(urlEncode(key)).append(valueConnect);
//			result.append(urlEncode(params.get(key))).append(keyConnect);
			result.append(key).append(valueConnect);
			result.append(params.get(key)).append(keyConnect);
		}
		String reStr = result.toString();
		reStr = reStr.substring(0,reStr.length()-1);
		return reStr;
	}
	
	/**
	 * 
	 * 获取Base64加密后的Hmac字符串
	 * 
	 * @param secret
	 * @param strToSign
	 * @return
	 */
	private static String getHmacSignStr(String secret,String strToSign){
		try {
			Mac mac = Mac.getInstance(ALGORITHM);
			mac.init(new SecretKeySpec(secret.getBytes(ENCODING), ALGORITHM));
			byte[] signData = mac.doFinal(strToSign.getBytes(ENCODING));
			String signature = new String(Base64Utils.encode(signData));
			return signature;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}


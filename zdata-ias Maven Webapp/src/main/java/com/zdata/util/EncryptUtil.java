package com.zdata.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

	static {
		try {
			MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static String encrypt(String pwd, String salt) {
		try {
			MessageDigest encoder = MessageDigest.getInstance("MD5");
			pwd = pwd + salt;
			encoder.reset();
			encoder.update(pwd.getBytes(Charset.forName("UTF8")));
			byte[] resultByte = encoder.digest();
			String result = new String(Hex.encodeHex(resultByte));
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isPasswordValid(String encPass, String rawPass, String salt) {
		return encrypt(rawPass, salt).equals(encPass);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*String encPass = encrypt("666666", "191189");
		String rawPass = "666666";
		String salt = "191189";
		//System.out.println(encPass);
		//System.out.println(isPasswordValid(encPass, rawPass, salt));
		long start  = System.currentTimeMillis();
		for(int i=0;i<100000; i++){
			MessageDigest encoder = MessageDigest.getInstance("MD5");
			encrypt(Math.random()+"111", "1111"+i);
		}*/
		System.out.println(encrypt("666666", "ZHYQ0001"));
		//System.out.println(encrypt("666666", "T0001"));
		//System.out.println(System.currentTimeMillis()  - start);
		//System.out.println(isPasswordValid("a321d8383a1566e1e1c54e5daf384eb7","666666","T0001"));
	}
}
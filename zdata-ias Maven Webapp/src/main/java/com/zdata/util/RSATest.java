package com.zdata.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class RSATest {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Map<String, String> keyMap = RSAUtils.createKeys(512);
		String publicKey = keyMap.get("publicKey");
		String privateKey = keyMap.get("privateKey");
		System.out.println("公钥:" + publicKey);
		System.out.println("私钥:" + privateKey);
		System.out.println("公钥加密——私钥解密");
		String str = "ZQDXKJY0001";
		System.out.println("明文:" + str);
		System.out.println("明文大小:" + str.getBytes().length);
		String decodedData;
		try {
			String encodedData=RSAUtils.privateEncrypt(str, RSAUtils.getPrivateKey(privateKey));
			System.out.println("密文:" + encodedData);
			decodedData=RSAUtils.publicDecrypt(encodedData, RSAUtils.getPublicKey(publicKey));
			System.out.println("解密后文字:" + decodedData);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		/*Map<String, String> keyMap = RSAUtils.createKeys(1024);
		String publicKey = keyMap.get("publicKey");
		String string= "Beb_XxTlEIWR_z3TRq4wr04KBj6LpdjEF5GfAqRIi1akTfcHLm7muVty_Ra_FmRwbH4ogEFmD8dkQJUcpvvDlR39C8bVyjY3_nHgzzOaCjD8Rw6g7c3NRNh_sSJzNo5F0qoX5ndRiCJf1gWEFtZNXpY99iOu3SSzgQTMvIWxvV4";
		String decodedData=RSAUtils.publicDecrypt(string, RSAUtils.getPublicKey(publicKey));
		System.out.println("解密后文字:" + decodedData);*/
	}
}

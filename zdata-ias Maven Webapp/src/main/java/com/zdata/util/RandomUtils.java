package com.zdata.util;

import java.io.Serializable;
import java.util.Random;

/**
 * 随机数
 * 
 * @author Administrator
 *
 */
public abstract class RandomUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int LOWERCASE = 0;
	public static final int CAPITAL = 1;
	public static final int NUMBER = 2;
	public static final int LOWERCASE_AND_NUMBER = 3;
	public static final int CAPITAL_AND_NUMBER = 4;
	public static final int LETTER_AND_NUMBER = 5;
	public static final String S_LOWERCASE = "abcdefghijklmnopqrstuvwxys";
	public static final String S_CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String S_NUMBER = "0123456789";
	public static final String S_LOWERCASE_AND_NUMBER = "012abcdefg345hijklmnopqr6789stuvwxyz";
	public static final String S_UPPERCASE_AND_NUMBER = "012ABCDEFG345HIJKLMNOPQR6789STUVWXYZ";
	public static final String S_LETTER_AND_NUMBER = "0123456789AaBbCcDdEeFfGg0123456789HhIiJjKkLlMmNnOoPpQqRr0123456789SsTtUuVvWwXxYyZz";
	public static final int length = 6;

	public static String generate(int type, int randomStringLength) {
		String source = getSourceByType(type);
		return generate(source, randomStringLength);
	}

	private static String getSourceByType(int type) {
		String source = null;
		switch (type) {
		case 0:
			source = "abcdefghijklmnopqrstuvwxys";
			break;
		case 1:
			source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case 2:
			source = "0123456789";
			break;
		case 3:
			source = "012abcdefg345hijklmnopqr6789stuvwxyz";
			break;
		case 4:
			source = "012ABCDEFG345HIJKLMNOPQR6789STUVWXYZ";
			break;
		case 6:
			source = "0123456789AaBbCcDdEeFfGg0123456789HhIiJjKkLlMmNnOoPpQqRr0123456789SsTtUuVvWwXxYyZz";
			break;
		case 5:
		default:
			source = "0123456789AaBbCcDdEeFfGg0123456789HhIiJjKkLlMmNnOoPpQqRr0123456789SsTtUuVvWwXxYyZz";
		}

		return source;
	}

	public static String generate(String source, int randomStringLength) {
		String randStr = source;
		StringBuffer generateRandStr = new StringBuffer("");
		int strLength = randStr.length();
		Random rand = new Random(System.currentTimeMillis() + 123L);
		int randStrLength = randomStringLength;
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(strLength);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return generateRandStr.toString();
	}

	public static String generate() {
		return generate(
				"0123456789AaBbCcDdEeFfGg0123456789HhIiJjKkLlMmNnOoPpQqRr0123456789SsTtUuVvWwXxYyZz",
				6);
	}

	public static void main(String[] args) {
		System.out.println(generate());
		System.out.println(generate(2, 4));
	}
}

package com.zdata.util;

import java.util.Map;


public class ConverUtil {

	private static StringBuffer sBuffer = new StringBuffer();
	
	public static void main(String[] args) {
		String str = "{ ‘A’: 1, ‘B.A’: 2, ‘B.B’: 3, ‘CC.D.E’: 4, ‘CC.D.F’: 5}";
		str = str.replaceAll("‘", "\"");
		str = str.replaceAll("’", "\"");
		System.out.println(str);
		JSONObject jsonObject = JSONObject.parseObject(str);
		sBuffer.append("{");
		for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			//包含对象
			if(entry.getKey().contains(".")){
				//
				//dtString(JSONObject.parseObject("{"+"\""+entry.getKey()+"\""+":"+entry.getValue()+"}"),sBuffer);
				String key = entry.getKey();
				Integer value = (Integer) entry.getValue();
				if (countChar(entry.getKey(),'.')==1) {
					//一个点
					sBuffer.append("\""+entry.getKey().substring(0,1)+"\""+":{\""+entry.getKey().substring(2)+"\":"+entry.getValue()+"}"+",");
				}else if (countChar(entry.getKey(),'.')==2){
					
				}
				
			}else{
				sBuffer.append("\""+entry.getKey()+"\""+":"+entry.getValue()+",");
			}
		}
		sBuffer.deleteCharAt(sBuffer.length() - 1);
		//dtString(jsonObject, sBuffer);
		sBuffer.append("}");
		System.out.println(sBuffer);
	}
	
	public static int countChar(String str,char ch) {
	    // 将字符串转换为字符数组
	    char[] chs = str.toCharArray();
	    // 定义变量zdcount存储字符串出现次数
	    int count = 0;
	    for(int i = 0;i < chs.length;i++) {
	        if(chs[i] == ch) {
	        count++;
	        }
	    }
	    return count;
	}
	
	//递归
	public static void dtString(JSONObject jsonObject,StringBuffer sBuffer) {
		for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			//包含对象
			if(entry.getKey().contains(".")){
				dtString(JSONObject.parseObject("{"+"\""+entry.getKey()+"\""+":"+entry.getValue()+"}"),sBuffer);
			}else{
				sBuffer.append("\""+entry.getKey()+"\""+":"+entry.getValue());
			}
		}
    }
	
}

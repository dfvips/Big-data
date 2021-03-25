package com.zdata.sycm.util;
/**
 * 
 * 短信发送请求Url枚举类
 * 
 * @author huangzhuozhen
 * @Time 2019年4月23日 下午5:05:30 
 */
public enum SmsRequestUrl {
	/*
	 * 一、调用失败结果如下：
1 未知错误 Unknown error 
2 服务暂不可用 Service temporarily unavailable 
3 未知的方法 Unsupported openapi method 
4 接口调用次数已达到设定的上限 Open api request limit reached 
5 请求来自未经授权的IP地址 Unauthorized client IP address 
6 无权限访问该用户数据 No permission to access user data 
7 来自该refer的请求无访问权限 No permission to access data for this referer 
100 请求参数无效 Invalid parameter 
101 api key无效 Invalid API key 
104 无效签名 Incorrect signature 
105 请求参数过多 Too many parameters 
106 未知的签名方法 Unsupported signature method 
107 timestamp参数无效 Invalid/Used timestamp parameter 
109 无效的用户资料字段名 Invalid user info field 
110 无效的access token Access token invalid or no longer valid 
111 access token过期 Access token expired 
210 用户不可见 User not visible 
211 获取未授权的字段 Unsupported permission 
212 没有权限获取用户的email No permission to access user email 
800 未知的存储操作错误 Unknown data store API error 
801 无效的操作方法 Invalid operation 
802 数据存储空间已超过设定的上限 Data store allowable quota was exceeded 
803 指定的对象不存在 Specified object cannot be found 
804 指定的对象已存在 Specified object already exists 
805 数据库操作出错，请重试 A database error occurred. Please try again 
900 访问的应用不存在 No such application exists 
1121	用户不存在
1157	Avino不能为空
2029 该帐号没有对应的通讯权限，如电话、短信、电话会议。
	 */
	
	/**
	 * 发送短信接口
	 */
	Send_Sms("http://api.189.cn/v2/emp/templateSms/sendSms"),
	
	/**
	 * 验证码短信接口
	 */
	Rand_Code("http://api.189.cn/v2/dm/randcode/sendSms"),
	
	/**
	 * 天翼开放平台令牌接口用于获取最终的授权访问令牌（Accesss Token，简称AT）。
	 * http://open.189.cn/index.php?m=content&c=index&a=lists&catid=62
	 */
	Access_Token("https://oauth.api.189.cn/emp/oauth2/v3/access_token"),
	
	
	
	;
	private final String value;
	
	SmsRequestUrl(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}


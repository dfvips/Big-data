package com.zdata.sycm.util;
/**
 * 
 * 短信发送响应结果
 * 
 * @author huangzhuozhen
 * @Time 2019年4月23日 下午5:16:48 
 */
public class SmsResponse {
	
	/**
	 * 请求状态
	 * <br> 短信发送、Token
	 */
	private String res_code;
	
	/**
	 * 提示状态
	 * <br> 短信发送、Token
	 */
	private String res_message;
	
	/**
	 * 	随机短信标识，标示验证码的唯一性，用来区分短信版本。格式为“AJXXXX”两位英文字符+四位数字。合作应用可以将该标识与请求发送的验证码建立对应关系，以便用户输入时精确校验。
	 */
	private String identifier;
	
	/**
	 * 短信下发的Unix时间戳
	 */
	private Long create_at; 
	
	/**
	 * Token-令牌
	 */
	private String access_token;
	/**
	 * Token-过期时间,单位秒
	 */
	private String expires_in;
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}
	public String getRes_message() {
		return res_message;
	}
	public void setRes_message(String res_message) {
		this.res_message = res_message;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Long getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Long create_at) {
		this.create_at = create_at;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	
}


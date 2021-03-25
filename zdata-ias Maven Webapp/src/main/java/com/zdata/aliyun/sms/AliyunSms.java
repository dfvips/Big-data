package com.zdata.aliyun.sms;

/**
 * 短信发送实体
 * @author Administrator
 *
 */
public class AliyunSms {

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 短信模板Id
	 */
	private String templateCode;

	/**
	 * 短信模板变量对应的实际值，JSON格式
	 */
	private String templateParam;
	
	private String time;
	
	private String code;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

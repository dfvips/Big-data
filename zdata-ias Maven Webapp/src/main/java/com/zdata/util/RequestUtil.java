package com.zdata.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
	
	private String requestURI;
	private Map<String, String> parameterMap;

	public RequestUtil() {
	}

	public RequestUtil(String requestURI) {
		this.requestURI = requestURI;
		parameterMap = new HashMap<String, String>();
	}

	public RequestUtil(String requestURI, Map<String, String> parameterMap) {
		this.requestURI = requestURI;
		this.parameterMap = parameterMap;
	}

	/**
	 * 获得指定名称的参数
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		String values = parameterMap.get(name);
		// if (values != null && values.length > 0) {
		// return values[0];
		// }
		return values;
	}

	/**
	 * 获得所有的参数名称
	 * 
	 * @return
	 */
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameterMap.keySet());
	}

	/**
	 * 获得指定名称的参数值(多个)
	 * 
	 * @param name
	 * @return
	 */
	public String getParameterValues(String name) {
		return parameterMap.get(name);
	}

	/**
	 * 获得请求的url地址
	 * 
	 * @return
	 */
	public String getRequestURI() {
		return requestURI;
	}

	/**
	 * 获得 参数-值Map
	 * 
	 * @return
	 */
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		buf.append("\n  url = ").append(this.requestURI);
		buf.append("\n  paramsMap = {");
		if (this.parameterMap.size() > 0) {
			for (Map.Entry<String, String> e : this.parameterMap.entrySet()) {
				buf.append(e.getKey()).append("=").append(e.getValue())
						.append(",");
			}
			buf.deleteCharAt(buf.length() - 1);
		}
		buf.append("}\n}");
		return buf.toString();
	}

	public String getParamsJsonStr() {
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		if (this.parameterMap.size() > 0) {
			for (Map.Entry<String, String> e : this.parameterMap.entrySet()) {
				buf.append(e.getKey()).append("=").append(e.getValue())
						.append(",");
			}
			buf.deleteCharAt(buf.length() - 1);
		}
		buf.append("}");
		return buf.toString();
	}

	// 剩下的函数,自己根据需求实现了,一般返回0或者null即可
	// 这里就不贴了,HttpServletRequest的接口方法也太多了

	/**
	 * 
	 * <p>Title: getIpAddr</p>  
	 * <p>Description: </p>  
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}

package com.zdata.util;

import java.util.HashMap;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.model.SysUser;

public class SessionUtils {
	
	public static SysUser getCurrentUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constant.Current_User.getValue());
		return user;
	}
	
	public static Map<Object, Object> getSessionMap(HttpServletRequest request){
		String token = getToken(request);
		RedisUtil redisUtil = new RedisUtil(null);
		Map<Object, Object> sessionMap = redisUtil.hmget("session_"+token);
		return sessionMap;
	}
	
	public static String getToken(HttpServletRequest request){
		String token = "";
		Cookie cookie = getCookieByName(request,"ias_token");
		if (cookie!=null) {
			token = cookie.getValue();
		}
		return token;
	}
	
	private static Cookie getCookieByName(HttpServletRequest request,String name){
		Map<String, Cookie> cookieMap=ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap=new HashMap<String, Cookie>();
		Cookie[] cookies=request.getCookies();
		if(null != cookies){
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}

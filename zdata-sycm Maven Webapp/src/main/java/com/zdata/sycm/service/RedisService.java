package com.zdata.sycm.service;

import java.util.Map;

public interface RedisService {

	public boolean set(final String key, Object value);
	
	public boolean set(final String key, Object value, Long expireTime);
	
	public boolean hasKey(String key);
	
	public Object get(String key);
	
	public Map<Object, Object> hmget(String key);
	
	public boolean hmset(String key, Map<String, Object> map);
	
	public boolean hmset(String key, Map<String, Object> map, long time);
	boolean expire(String key, long time);
	
	public void setString(String key,String str);
	
	public void setStringAndTimeOut(String key,String str,Integer expise);
	
	public String getString(String key);
	
	public void delKey(String key);
	
	public void delKeys(String key);
}

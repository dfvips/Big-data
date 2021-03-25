package com.zdata.sycm.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.zdata.sycm.service.RedisService;


@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;  
	 
	@SuppressWarnings("rawtypes")
	public void init() {
		RedisSerializer stringSerializer = new StringRedisSerializer();
		this.redisTemplate.setKeySerializer(stringSerializer);
		this.redisTemplate.setValueSerializer(stringSerializer);
		this.redisTemplate.setHashKeySerializer(stringSerializer);
		this.redisTemplate.setHashValueSerializer(stringSerializer);
    }
	
	@Override
	public boolean set(String key, Object value) {
		boolean result = false;
        try {
        	init();
			redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}

	@Override
	public boolean set(String key, Object value, Long expireTime) {
		boolean result = false;
        try {
        	init();
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}

	@Override
	public boolean hasKey(String key) {
		try {
			init();
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Object get(String key) {
		init();
        return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param:        @param key
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:46:45 
	 */
	@Override
    public Map<Object, Object> hmget(String key) {
		init();
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param:        @param key
     * @param:        @param map
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:49:06 
     */
	@Override
    public boolean hmset(String key, Map<String, Object> map) {
        try {
        	init();
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param:        @param key
     * @param:        @param map
     * @param:        @param time
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:50:28 
     */
	@Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
        	init();
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 指定缓存失效时间
	 * @param:        @param key
	 * @param:        @param time
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:30:15 
	 */
	@Override
	public boolean expire(String key, long time) {
        try {
            if (time > 0) {
            	init();
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	@Override
	public void setString(String key,String str){
		init();
    	redisTemplate.opsForValue().set(key, str);
    }
    
	@Override
    public void setStringAndTimeOut(String key,String str,Integer expise){
		init();
    	redisTemplate.opsForValue().set(key, str);
        redisTemplate.expire(key, expise, TimeUnit.SECONDS);
    }

	@Override
    public String getString(String key){
		init();
    	return redisTemplate.opsForValue().get(key).toString();
    }

	@Override
	public void delKey(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void delKeys(String key) {
		Set<String> keys = redisTemplate.keys(key+"*");
		redisTemplate.delete(keys);
	}

}

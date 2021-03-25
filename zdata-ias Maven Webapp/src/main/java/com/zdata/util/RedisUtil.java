package com.zdata.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RedisUtil(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
	
	/**
	 * 指定缓存失效时间
	 * @param:        @param key
	 * @param:        @param time
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:30:15 
	 */
	public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 根据key 获取过期时间
	 * @param:        @param key
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:30:53 
	 */
	public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
	
	/**
	 * 判断key是否存在
	 * @param:        @param key
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:31:12 
	 */
	public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 删除缓存
	 * @param:        @param key  可以传一个值 或多个  
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:37:05 
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
	
	/**
	 * 普通缓存获取
	 * @param:        @param key
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:39:32 
	 */
	public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
	
	/**
	 * 普通缓存放入
	 * @param:        @param key
	 * @param:        @param value
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:40:21 
	 */
	public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	/**
	 * 普通缓存放入并设置时间
	 * @param:        @param key
	 * @param:        @param value
	 * @param:        @param time
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:41:55 
	 */
	public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	/**
	 * 递增
	 * @param:        @param key
	 * @param:        @param delta
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:42:42 
	 */
	public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

	/**
	 * 递减
	 * @param:        @param key
	 * @param:        @param delta
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:43:22 
	 */
	public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

	/**
	 * HashGet
	 * @param:        @param key
	 * @param:        @param item
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:46:19 
	 */
	public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

	/**
	 * 获取hashKey对应的所有键值
	 * @param:        @param key
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年1月7日 上午10:46:45 
	 */
    public Map<Object, Object> hmget(String key) {
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
    public boolean hmset(String key, Map<String, Object> map) {
        try {
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
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
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
     * 向一张hash表中放入数据,如果不存在将创建
     * @param:        @param key
     * @param:        @param item
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:50:45 
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param:        @param key
     * @param:        @param item
     * @param:        @param value
     * @param:        @param time
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:54:41 
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 删除hash表中的值
     * @param:        @param key
     * @param:        @param item    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:55:15 
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param:        @param key
     * @param:        @param item
     * @param:        @param by
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:56:44 
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param:        @param key
     * @param:        @param item
     * @param:        @param by
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:57:07 
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     * @param:        @param key
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:57:28 
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param:        @param key
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:57:42 
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param:        @param key
     * @param:        @param values
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:57:59 
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param:        @param key
     * @param:        @param time
     * @param:        @param values
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:58:15 
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param:        @param key
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:58:33 
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param:        @param key
     * @param:        @param values
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:58:51 
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     * @param:        @param key
     * @param:        @param start
     * @param:        @param end
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:59:09 
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

 
    /**
     * 获取list缓存的长度
     * @param:        @param key
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:59:21 
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param:        @param key
     * @param:        @param index
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午10:59:39 
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param:        @param key
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:00:15 
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param:        @param key
     * @param:        @param value
     * @param:        @param time
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:00:28 
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param:        @param key
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:00:45 
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param:        @param key
     * @param:        @param value
     * @param:        @param time
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:00:59 
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param:        @param key
     * @param:        @param index
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:01:12 
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param:        @param key
     * @param:        @param count
     * @param:        @param value
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:01:26 
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            return 0;
        }
    }
    
    // ===============================sorted set=================================

    /**
     * 向有序集合添加一个成员的
     * @param:        @param key
     * @param:        @param member
     * @param:        @param score
     * @param:        @param time
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:01:45 
     */
    public boolean zadd(String key, Object member, double score, long time) {
        try {
            redisTemplate.opsForZSet().add(key, member, score);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT] 
		通过分数返回有序集合指定区间内的成员
     * @param:        @param key
     * @param:        @param minScore
     * @param:        @param maxScore
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:02:05 
     */
    public Set<Object> zRangeByScore(String key, double minScore, double maxScore) {
    	try {
    		return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * ZSCORE key member 
		返回有序集中，成员的分数值
     * @param:        @param key
     * @param:        @param member
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:02:23 
     */
    public Double zscore(String key, Object member) {
    	try {
    		return redisTemplate.opsForZSet().score(key, member);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * ZRANK key member 返回有序集合中指定成员的索引
     * @param:        @param key
     * @param:        @param member
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:02:36 
     */
    public Long zrank(String key, Object member) {
    	try {
    		return redisTemplate.opsForZSet().rank(key, member);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * Zscan 迭代有序集合中的元素（包括元素成员和元素分值）
     * @param:        @param key
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年1月7日 上午11:03:19 
     */
    public Cursor<ZSetOperations.TypedTuple<Object>> zscan(String key) {
    	try {
    		Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, ScanOptions.NONE);
    		return cursor;
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    public void setString(String key,String str){
    	redisTemplate.opsForValue().set(key, str);
    }
    
    public void setStringAndTimeOut(String key,String str,Integer expise){
    	redisTemplate.opsForValue().set(key, str);
        redisTemplate.expire(key, expise, TimeUnit.SECONDS);
    }

    public String getString(String key){
    	return redisTemplate.opsForValue().get(key).toString();
    }

}

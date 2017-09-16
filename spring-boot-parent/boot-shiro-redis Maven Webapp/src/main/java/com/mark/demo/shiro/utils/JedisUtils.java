package com.mark.demo.shiro.utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.collect.Maps;
import com.mark.demo.shiro.constant.CharsetConst;
import com.mark.demo.shiro.exception.JedisHandleException;

import redis.clients.jedis.Jedis;


public class JedisUtils
{
    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    
    private static RedisTemplate<String,Object> redisTemplate;
    
    
    
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
     * 根据键值从Redis中获取字符串值
     * @param key 缓存键值
     * @return String 缓存数据
     */
    public static String get(String key)
    {
        String value = null;
        if (redisTemplate.hasKey(key))
        {
            value = (String)redisTemplate.opsForValue().get(key);
            value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
        }
        
        return value;
    }
    
    /**
     * 根据键值及缓存生命周期将指定的字符串写入Redis
     * @param key 键值
     * @param value 字符串数据
     * @param cacheSeconds 缓存生命周期
     * @return String 写入结果(OK：表示成功，否则失败)
     */
    public static String set(String key, String value, int cacheSeconds)
    {
        redisTemplate.opsForValue().set(key, value, cacheSeconds, TimeUnit.SECONDS);
        return value;
        
    }
    
    /**
     * 获取缓存对象
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key)
    {
        Object value = null;
        if (redisTemplate.hasKey(key))
        {
            value = redisTemplate.opsForValue().get(key);
        }
        
        return value;
    }
    
    /**
     * 
     * @param key
     * @param object
     */
    public static void setObject(String key, Object value, Integer cacheSeconds)
    {
    	redisTemplate.opsForValue().set(key, value, cacheSeconds, TimeUnit.SECONDS);
    }
    
    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public static List<Object> getListObject(String key)
    {
    	
        List<Object> value = redisTemplate.opsForList().range(key, 0, -1);
        
        return value;
    }
    
    
    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds)
    {
        long result = redisTemplate.opsForList().rightPushAll(key, value);
        redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds)
    {
    	long result = redisTemplate.opsForList().rightPushAll(key, value);
        redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listAdd(String key, String ... value)
    {
    	long result = redisTemplate.opsForList().rightPushAll(key, value);
        return result;
    }
    
    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listObjectAdd(String key, Object ... value)
    {
    	long result = redisTemplate.opsForList().rightPushAll(key, value);
        return result;
    }
    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key)
    {
    	return redisTemplate.opsForSet().members(key);
    }
    
    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<String> value, int cacheSeconds)
    {
        long result = redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds)
    {
    	long result = redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetAdd(String key, String ... value)
    {
    	long result = redisTemplate.opsForSet().add(key, value);
        return result;
    }
    
    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetObjectAdd(String key, Object ... value)
    {
        long result = redisTemplate.opsForSet().add(key, value);
        return result;
    }
    
    public static List<Object> getMapFieldValues(String key){
    	return redisTemplate.opsForHash().values(key);
    }
    
    public static Set<String> getMapFieldKeys(String key){
    	Set<Object> objectList=redisTemplate.opsForHash().keys(key);
    	Iterator it=objectList.iterator();
    	Set<String> ret=new HashSet<String>();
    	while(it.hasNext()){
    		ret.add((String)it.next());
    	}
    	return ret;
    }
    
    public static int getMapLen(String key){
    	return redisTemplate.opsForHash().size(key).intValue();
    }
    
    public static boolean removeMapField(String key,String field){
    	return redisTemplate.opsForHash().delete(key, field)>=1;
    }
    
    public static boolean removeMapField(String key,byte[] field){
    	return redisTemplate.opsForHash().delete(key, field)>=1;
    }
    
    public static boolean setMapField(String key,String field,Object value){
    	redisTemplate.opsForHash().put(key, field, value);
    	return true;
    }
    
    /*public static boolean setMapField(String key,byte[] field,Object value){
    	redisTemplate.opsForHash().put(key, field, value);
    	return true;
    }*/
    
    /**
     * 获取map中的一个字段
     * @param key
     * @param field
     * @return
     */
    public static Object getMapFiled(String key,String field){
    	Object value=redisTemplate.opsForHash().get(key, field);
        return value;
    }
    
   /* public static Object getMapFiled(String key,byte[] field){
    	Object value=redisTemplate.opsForHash().get(key, field);
        return value;
    }*/
    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String key)
    {
    	Map<Object,Object> objectMap=redisTemplate.opsForHash().entries(key);
    	Map<String,String> strMap=new HashMap<String,String>();
    	Set<Map.Entry<Object,Object>> entirys=objectMap.entrySet();
    	Iterator<Map.Entry<Object,Object>> it=entirys.iterator();
    	while(it.hasNext()){
    		Map.Entry<Object,Object> entiry=it.next();
    		strMap.put((String)entiry.getKey(),(String)entiry.getValue());
    	}
    	return strMap;
    }
    
    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key)
    {
    	Map<Object,Object> objectMap=redisTemplate.opsForHash().entries(key);
    	Map<String,Object> strMap=new HashMap<String,Object>();
    	Set<Map.Entry<Object,Object>> entirys=objectMap.entrySet();
    	Iterator<Map.Entry<Object,Object>> it=entirys.iterator();
    	while(it.hasNext()){
    		Map.Entry<Object,Object> entiry=it.next();
    		strMap.put((String)entiry.getKey(),entiry.getValue());
    	}
    	return strMap;
    }
    
    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static void setMap(String key, Map<String, String> value, int cacheSeconds)
    {
    	redisTemplate.opsForHash().putAll(key, value);
    	redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
        
    }
    
    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static void setObjectMap(String key, Map<String, Object> value, int cacheSeconds)
    {
    	redisTemplate.opsForHash().putAll(key, value);
    	redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
    }
    
    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static void mapPut(String key, Map<String, String> value)
    {
    	redisTemplate.opsForHash().putAll(key, value);
    }
    
    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static void mapObjectPut(String key, Map<String, Object> value)
    {
    	redisTemplate.opsForHash().putAll(key, value);
    }
    
    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapRemove(String key, String mapKey)
    {
    	return redisTemplate.opsForHash().delete(key,mapKey);
    }
    
    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapObjectRemove(String key, String mapKey)
    {
    	return redisTemplate.opsForHash().delete(key,mapKey);
    }
    
    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapExists(String key, String mapKey)
    {
        return redisTemplate.opsForHash().hasKey(key, mapKey);
        
    }
    
    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapObjectExists(String key, String mapKey)
    {
    	return redisTemplate.opsForHash().hasKey(key, mapKey);
    }
    
    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static void del(String key)
    {
    	redisTemplate.delete(key);
    }
    
    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static void delObject(String key)
    {
    	redisTemplate.delete(key);
    }
    
    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean exists(String key)
    {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key)
    {
    	return redisTemplate.hasKey(key);
    }
    
    public static boolean expire(String key, int expireSeconds){
    	return redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }
    
    
}

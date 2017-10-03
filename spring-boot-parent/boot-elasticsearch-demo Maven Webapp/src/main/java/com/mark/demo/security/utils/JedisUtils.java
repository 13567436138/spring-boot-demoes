package com.mark.demo.security.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mark.demo.security.constant.CharsetConst;
import com.mark.demo.security.exception.JedisHandleException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisDataException;


public class JedisUtils
{
    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    
    /**
     * 私有构造器，防止类的实例化操作
     */
    private JedisUtils()
    {
    }
    
    // 从spring引入，可以考虑编码实现，避免对spring的依赖
    //public static JedisSlotBasedConnectionHandler jedisSlotBasedConnectionHandler = SpringUtils.getBean(JedisSlotBasedConnectionHandler.class);
    private static ShardedJedisPool pool= SpringUtils.getBean(ShardedJedisPool.class);
    /**
     * 根据键值从Redis中获取字符串值
     * @param key 缓存键值
     * @return String 缓存数据
     */
    public static String get(String key)
    {
        String value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
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
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.set(key, value);
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);// 这里返回的是long，如果为1估计是成功，但具体意义在源码里没有找到注释
            }
        }
        catch (JedisDataException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 获取缓存对象
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key)
    {
        Object value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                value = ObjectUtils.toObject(jedis.get(ObjectUtils.getBytes(key)));
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    /**
     * 
     * @param key
     * @param object
     */
    public static String setObject(String key, Object value, Integer cacheSeconds)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.set(ObjectUtils.getBytes(key), ObjectUtils.toBytes(value));
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public static List<String> getListString(String key)
    {
        List<String> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value = jedis.lrange(key, 0, -1);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String key)
    {
        List<Object> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                List<byte[]> list = jedis.lrange(ObjectUtils.getBytes(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list)
                {
                    value.add(ObjectUtils.toObject(bs));
                }
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[]) value.toArray());
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                jedis.del(key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value)
            {
                list.add(ObjectUtils.toBytes(o));
            }
            result = jedis.rpush(ObjectUtils.getBytes(key), (byte[][]) list.toArray());
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.rpush(key, value);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value)
            {
                list.add(ObjectUtils.toBytes(o));
            }
            result = jedis.rpush(ObjectUtils.getBytes(key), (byte[][]) list.toArray());
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Set<String> getSet(String key)
    {
        Set<String> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value = jedis.smembers(key);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    public static Set<byte[]> getSet(byte[] key)
    {
        Set<byte[]> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(new String(key,"UTF-8"));
            if (jedis.exists(key))
            {
                value = jedis.smembers(key);
            }
        }catch(UnsupportedEncodingException e2){
       	 LoggerUtils.logError(logger, e2.getMessage());
       }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key)
    {
        Set<Object> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                value = Sets.newHashSet();
                Set<byte[]> set = jedis.smembers(ObjectUtils.getBytes(key));
                for (byte[] bs : set)
                {
                    value.add(ObjectUtils.toObject(bs));
                }
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                jedis.del(key);
            }
            result = jedis.sadd(key, (String[]) value.toArray());
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                jedis.del(key);
            }
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value)
            {
                set.add(ObjectUtils.toBytes(o));
            }
            result = jedis.sadd(ObjectUtils.getBytes(key), (byte[][]) set.toArray());
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.sadd(key, value);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    public static long setSetAdd(byte[] key, byte[] ... value )
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(new String(key,"UTF-8"));
            result = jedis.sadd(key, value);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        } catch (UnsupportedEncodingException e) {
        	LoggerUtils.logError(logger, e.getMessage());
		}
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    public static long setSetAdd(byte[] key, Set<byte[]> value )
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(new String(key,"UTF-8"));
            Iterator<byte[]> it=value.iterator();
            while(it.hasNext()){
            	result = jedis.sadd(key,it.next() );
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        } catch (UnsupportedEncodingException e) {
        	LoggerUtils.logError(logger, e.getMessage());
		}
        finally
        {
            releaseConnection(jedis);
        }
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
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value)
            {
                set.add(ObjectUtils.toBytes(o));
            }
            result = jedis.rpush(ObjectUtils.getBytes(key), (byte[][]) set.toArray());
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    public static List<byte[]> getMapFieldValues(String key){
    	Jedis jedis=null;
    	
    	try{
    		jedis=getConnection(key);
    		return jedis.hvals(key.getBytes());
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return null;
    }
    
    public static Set<String> getMapFieldKeys(String key){
    	Jedis jedis=null;
    	
    	try{
    		jedis=getConnection(key);
    		return jedis.hkeys(key);
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return null;
    }
    
    public static int getMapLen(String key){
    	Jedis jedis=null;
    	int value=0;
    	try{
    		jedis=getConnection(key);
    		value=jedis.hlen(key).intValue();
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return value;
    }
    
    public static boolean removeMapField(String key,String field){
    	Jedis jedis=null;
    	try{
    		jedis=getConnection(key);
    		jedis.hdel(key, field);
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return true;
    }
    
    public static boolean removeMapField(String key,byte[] field){
    	Jedis jedis=null;
    	try{
    		jedis=getConnection(key);
    		jedis.hdel(key.getBytes(), field);
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return true;
    }
    
    public static boolean setMapField(String key,String field,Object value){
    	Jedis jedis=null;
    	
    	try{
    		jedis=getConnection(key);
    		jedis.hset(key.getBytes(), field.getBytes(), ObjectUtils.serialize(value));
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return true;
    }
    
    public static boolean setMapField(String key,byte[] field,Object value){
    	Jedis jedis=null;
    	
    	try{
    		jedis=getConnection(key);
    		jedis.hset(key.getBytes(), field, ObjectUtils.serialize(value));
    	}catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }finally{
    		releaseConnection(jedis);
    	}
    	return true;
    }
    
    /**
     * 获取map中的一个字段
     * @param key
     * @param field
     * @return
     */
    public static Object getMapFiled(String key,String field){
    	Jedis jedis = null;
    	Object value=null;
    	try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value = jedis.hget(key, field);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    public static Object getMapFiled(String key,byte[] field){
    	Jedis jedis = null;
    	Object value=null;
    	try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value =ObjectUtils.unserialize(jedis.hget(key.getBytes(), field));
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String key)
    {
        Map<String, String> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                value = jedis.hgetAll(key);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key)
    {
        Map<String, Object> value = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                value = Maps.newHashMap();
                Map<byte[], byte[]> map = jedis.hgetAll(ObjectUtils.getBytes(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet())
                {
                    value.put(new String(e.getKey(), Charset.forName(CharsetConst.CHARSET_UT)), ObjectUtils.toObject(e.getValue()));
                }
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return value;
    }
    
    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setMap(String key, Map<String, String> value, int cacheSeconds)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet())
            {
                map.put(ObjectUtils.getBytes(e.getKey()), ObjectUtils.toBytes(e.getValue()));
            }
            result = jedis.hmset(ObjectUtils.getBytes(key), (Map<byte[], byte[]>) map);
            if (cacheSeconds != 0)
            {
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapPut(String key, Map<String, String> value)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.hmset(key, value);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapObjectPut(String key, Map<String, Object> value)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet())
            {
                map.put(ObjectUtils.getBytes(e.getKey()), ObjectUtils.toBytes(e.getValue()));
            }
            result = jedis.hmset(ObjectUtils.getBytes(key), (Map<byte[], byte[]>) map);
            logger.debug("mapObjectPut {} = {}", key, value);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapRemove(String key, String mapKey)
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.hdel(key, mapKey);
            logger.debug("mapRemove {}  {}", key, mapKey);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + mapKey + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapObjectRemove(String key, String mapKey)
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.hdel(ObjectUtils.getBytes(key), ObjectUtils.getBytes(mapKey));
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + mapKey + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapExists(String key, String mapKey)
    {
        boolean result = false;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.hexists(key, mapKey);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + mapKey + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapObjectExists(String key, String mapKey)
    {
        boolean result = false;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.hexists(ObjectUtils.getBytes(key), ObjectUtils.getBytes(mapKey));
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + mapKey + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static long del(String key)
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(key))
            {
                result = jedis.del(key);
            }
            else
            {
                logger.debug("del {} not exists", key);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static long delObject(String key)
    {
        long result = 0;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            if (jedis.exists(ObjectUtils.getBytes(key)))
            {
                result = jedis.del(ObjectUtils.getBytes(key));
                logger.debug("delObject {}", key);
            }
            else
            {
                logger.debug("delObject {} not exists", key);
            }
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean exists(String key)
    {
        boolean result = false;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.exists(key);
            logger.debug("exists {}", key);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key)
    {
        boolean result = false;
        Jedis jedis = null;
        try
        {
            jedis = getConnection(key);
            result = jedis.exists(ObjectUtils.getBytes(key));
            logger.debug("existsObject {}", key);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, key + "===" + e.getMessage());
        }
        finally
        {
            releaseConnection(jedis);
        }
        return result;
    }
    
    public static boolean expire(String key, int expireSeconds){
    	 long resultInt = 0;
         Jedis jedis = null;
         try
         {
             jedis = getConnection(key);
             resultInt = jedis.expire(key, expireSeconds);
         }
         catch (JedisHandleException e)
         {
             LoggerUtils.logError(logger, key + "===" + e.getMessage());
         }
         finally
         {
             releaseConnection(jedis);
         }
         return resultInt==1L;
    }
    
    /**
     * 从Redis集群中根据CRC16算法获取当前key对象的Redis服务器操作对象
     * @param key 缓存key值
     * @return Jedis Jedis
     */
    private static Jedis getConnection(String key)
    {
    	Jedis jedis=pool.getResource().getShard(key);
       /* Jedis jedis = null;
        try
        {
            jedis = jedisSlotBasedConnectionHandler.getConnectionFromSlot(JedisClusterCRC16.getSlot(key));
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }*/
        return jedis;
    }
    
    /**
     * 释放Redis服务器连接
     * @param jedis Jedis
     */
    private static void releaseConnection(Jedis jedis)
    {
        try
        {
        	//JedisPool pool=jedisSlotBasedConnectionHandler.getNodes().get(jedis.getClient().getHost()+":"+jedis.getClient().getPort());
            //pool.returnResource(jedis);
        	jedis.close();
        	//jedisSlotBasedConnectionHandler.returnConnection(jedis);
        }
        catch (JedisHandleException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
    }
}

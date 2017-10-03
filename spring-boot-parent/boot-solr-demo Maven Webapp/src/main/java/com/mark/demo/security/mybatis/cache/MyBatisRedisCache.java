package com.mark.demo.security.mybatis.cache;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;

import com.mark.demo.security.utils.JedisUtils;
import com.mark.demo.security.utils.ObjectUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
public class MyBatisRedisCache implements Cache,Serializable{
	public static final String mybatis_cache_prefix="mybatis_cache";

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;

	public MyBatisRedisCache(final String id) {
	    if (id == null) {
	        throw new IllegalArgumentException("缓存没有初始化id");
	    }
	    this.id = id;
	}

	@Override
	public String getId() {
	    return this.id;
	}

	@Override
	public int getSize() {
		
	    return JedisUtils.getMapLen(mybatis_cache_prefix);
	}

	@Override
	public void putObject(Object key, Object value) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
	    JedisUtils.setMapField(mybatis_cache_prefix+myKey, cacheKey.toString().getBytes(), value);
	}
	@Override
	public Object getObject(Object key) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
	    return JedisUtils.getMapFiled(mybatis_cache_prefix+myKey, cacheKey.toString().getBytes());
	    
	}

	@Override
	public Object removeObject(Object key) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
	    Object ret=JedisUtils.getMapFiled(mybatis_cache_prefix+myKey, ObjectUtils.serialize(key));
		JedisUtils.removeMapField(mybatis_cache_prefix+myKey, ObjectUtils.serialize(key));
		return ret;
	}

	@Override
	public void clear() {
	    JedisUtils.del(mybatis_cache_prefix);
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
	    return readWriteLock;
	}

}

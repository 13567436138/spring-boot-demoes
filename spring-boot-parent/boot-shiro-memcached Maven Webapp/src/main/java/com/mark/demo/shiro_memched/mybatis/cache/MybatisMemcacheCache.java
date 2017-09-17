package com.mark.demo.shiro_memched.mybatis.cache;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;

import com.mark.demo.shiro_memched.utils.MemcachedUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月15日
*
*/
public class MybatisMemcacheCache implements Cache {
	public static final String mybatis_cache_prefix="mybatis_cache";

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;

	public MybatisMemcacheCache(final String id) {
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
		Set<String> primaryKeys=(Set<String>)MemcachedUtils.get(mybatis_cache_prefix);
		Iterator<String> it=primaryKeys.iterator();
		int size=0;
		while(it.hasNext()){
			String primaryKey=it.next();
			size+=MemcachedUtils.getGroupLen(primaryKey);
		}
	    return size;
	}

	@Override
	public void putObject(Object key, Object value) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
		MemcachedUtils.setGroupField(mybatis_cache_prefix+myKey, cacheKey.toString(), value);
		Set<String> primaryKeys=(Set<String>)MemcachedUtils.get(mybatis_cache_prefix);
		if(primaryKeys==null){
			primaryKeys=new HashSet<String>();
		}
		primaryKeys.add(mybatis_cache_prefix+myKey);
		MemcachedUtils.set(mybatis_cache_prefix, primaryKeys);
	}
	@Override
	public Object getObject(Object key) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
		return MemcachedUtils.get(cacheKey.toString());
	    
	}

	@Override
	public Object removeObject(Object key) {
		CacheKey cacheKey=(CacheKey)key;
		String [] keyAry=cacheKey.toString().split(":");
		String myKey=keyAry[2];
	    Object ret=MemcachedUtils.get((String)key);
	    MemcachedUtils.delGroupField(mybatis_cache_prefix+myKey, (String)key);
	    Set<String> primaryKeys=(Set<String>)MemcachedUtils.get(mybatis_cache_prefix);
		if(primaryKeys!=null){
			primaryKeys.remove(mybatis_cache_prefix+myKey);
			MemcachedUtils.set(mybatis_cache_prefix, primaryKeys);
		}
		return ret;
	}

	@Override
	public void clear() {
		Set<String> primaryKeys=(Set<String>)MemcachedUtils.get(mybatis_cache_prefix);
		Iterator<String>it=primaryKeys.iterator();
		while(it.hasNext()){
			String primaryKey=it.next();
			MemcachedUtils.delGroup(primaryKey);
		}
		MemcachedUtils.delete(mybatis_cache_prefix);
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
	    return readWriteLock;
	}


}

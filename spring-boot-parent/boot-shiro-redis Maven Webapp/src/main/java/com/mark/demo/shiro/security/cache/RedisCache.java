package com.mark.demo.shiro.security.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mark.demo.shiro.utils.JedisUtils;
import com.mark.demo.shiro.utils.ObjectUtils;

public class RedisCache<K, V> implements Cache<K, V>
{
    private Logger logger       = LoggerFactory.getLogger(getClass());
    
    private String cacheKeyName = null;
    
    public RedisCache(String cacheKeyName)
    {
        this.cacheKeyName = cacheKeyName;
    }
    
    @Override
    public V get(K key) throws CacheException
    {
        return (V)JedisUtils.getMapFiled(cacheKeyName,String.valueOf(key));
    }
    
    @Override
    public V put(K key, V value) throws CacheException
    {
        JedisUtils.setMapField(cacheKeyName,String.valueOf(key), (Object)value);
        return value;
    }
    
    @Override
    public V remove(K key) throws CacheException
    {
        V value = (V)JedisUtils.getMapFiled(cacheKeyName,String.valueOf(key));
        JedisUtils.removeMapField(cacheKeyName,String.valueOf(key));
        
        return value;
    }
    
    @Override
    public void clear() throws CacheException
    {
    	JedisUtils.del(cacheKeyName);
    }
    
    @Override
    public int size()
    {
        return JedisUtils.getMapLen(cacheKeyName);
    }
    
    @Override
    public Set<K> keys()
    {
        return (Set<K>)JedisUtils.getMapFieldKeys(cacheKeyName);
    }
    
    @Override
    public Collection<V> values()
    {
    	Collection<V> col=new ArrayList<V>();
        List<byte[]>objects=JedisUtils.getMapFieldValues(cacheKeyName);
        for(int i=0;i<objects.size();i++){
        	byte[] object=objects.get(i);
        	col.add(((V)ObjectUtils.unserialize(object)));
        }
        return col;
    }
}
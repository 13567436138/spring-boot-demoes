package com.mark.demo.shiro_memched.security.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mark.demo.shiro_memched.utils.MemcachedUtils;

public class MemcachedCache<K, V> implements Cache<K, V>
{
    private Logger logger       = LoggerFactory.getLogger(getClass());
    
    private String cacheKeyName = null;
    
    public MemcachedCache(String cacheKeyName)
    {
        this.cacheKeyName = cacheKeyName;
    }
    
    @Override
    public V get(K key) throws CacheException
    {
        //return (V)JedisUtils.getMapFiled(cacheKeyName,String.valueOf(key));
    	return (V)MemcachedUtils.get(String.valueOf(key));
    }
    
    @Override
    public V put(K key, V value) throws CacheException
    {
        //JedisUtils.setMapField(cacheKeyName,String.valueOf(key), (Object)value);
    	MemcachedUtils.setGroupField(cacheKeyName, String.valueOf(key), (Object)value);
        return value;
    }
    
    @Override
    public V remove(K key) throws CacheException
    {
        //V value = (V)JedisUtils.getMapFiled(cacheKeyName,String.valueOf(key));
        //JedisUtils.removeMapField(cacheKeyName,String.valueOf(key));
    	V value=(V)MemcachedUtils.get(String.valueOf(key));
        MemcachedUtils.delGroupField(cacheKeyName, String.valueOf(key));
        return value;
    }
    
    @Override
    public void clear() throws CacheException
    {
    	MemcachedUtils.delGroup(cacheKeyName);
    	//JedisUtils.del(cacheKeyName);
    }
    
    @Override
    public int size()
    {
        //return JedisUtils.getMapLen(cacheKeyName);
    	Set<K> fields=(Set<K>)MemcachedUtils.get(cacheKeyName);
    	if(fields==null){
    		return 0;
    	}else{
    		return fields.size();
    	}
    }
    
    @Override
    public Set<K> keys()
    {
        //return (Set<K>)JedisUtils.getMapFieldKeys(cacheKeyName);
    	Set<K> fields=(Set<K>)MemcachedUtils.get(cacheKeyName);
    	return fields;
    }
    
    @Override
    public Collection<V> values()
    {
    	/*Collection<V> col=new ArrayList<V>();
        List<byte[]>objects=JedisUtils.getMapFieldValues(cacheKeyName);
        for(int i=0;i<objects.size();i++){
        	byte[] object=objects.get(i);
        	col.add(((V)ObjectUtils.unserialize(object)));
        }
        return col;*/
        
        Collection<V> col=new ArrayList<V>();
        Set<K>fields=(Set<K>)MemcachedUtils.get(cacheKeyName);
        Iterator<K> it=fields.iterator();
        while(it.hasNext()){
        	K k=it.next();
        	V v=(V)MemcachedUtils.get(String.valueOf(k));
        	col.add(v);
        }
        return col;
    }
}
package com.mark.demo.security.mybatis.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.mark.demo.security.utils.JedisUtils;
import com.mark.demo.security.utils.PropertiesLoader;

public class RedisCachingManagerImpl implements RedisCachingManager{
	
	//每一个statementId 更新依赖的statementId集合
	private Map<String,Set<String>> observers=new ConcurrentHashMap<String,Set<String>>();
	
	private boolean initialized = false;
	private boolean cacheEnabled = false;
	
	private volatile static RedisCachingManagerImpl enhancedCacheManager;

	private RedisCachingManagerImpl(){}
	public static RedisCachingManagerImpl getInstance()
	{
		if(enhancedCacheManager==null){
			synchronized (RedisCachingManagerImpl.class) {
				if(enhancedCacheManager==null){
					enhancedCacheManager=new RedisCachingManagerImpl();
				}
			}
		}
		return enhancedCacheManager;
	}
	
	public void clearRelatedCaches(final Set<String> set) {
		for(String observable:set)
		{
			Set<String> relatedStatements = observers.get(observable);
			for(String statementId:relatedStatements)
			{
				JedisUtils.del(MyBatisRedisCache.mybatis_cache_prefix+statementId);
			}
		}
	}
	public boolean isInitialized() {
		return initialized;
	}
	
	public void initialize(Properties properties)
	{
		PropertiesLoader loader=new PropertiesLoader("mybatis.properties");
		String dependency = loader.getProperty("dependencys");
		if(!("".equals(dependency) || dependency==null))
		{
			InputStream inputStream;
			try 
			{
					inputStream = Resources.getResourceAsStream(dependency);
					XPathParser parser = new XPathParser(inputStream);
					List<XNode> statements = parser.evalNodes("/dependencies/statements/statement");
					for(XNode node :statements)
					{
						Set<String> temp = new HashSet<String>();
						List<XNode> obs = node.evalNodes("observer");
						for(XNode observer:obs)
						{
							temp.add(observer.getStringAttribute("id"));
						}
						this.observers.put(node.getStringAttribute("id"), temp);
					}
					initialized = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//cacheEnabled
		String cacheEnabled = properties.getProperty("cacheEnabled", "true");
		if("true".equals(cacheEnabled))
		{
			this.cacheEnabled = true;
		}
	}
	
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}
}


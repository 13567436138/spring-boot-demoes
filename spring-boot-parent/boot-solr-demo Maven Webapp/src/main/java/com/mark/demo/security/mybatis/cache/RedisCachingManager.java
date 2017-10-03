package com.mark.demo.security.mybatis.cache;

import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.cache.Cache;


/**
 * Global Cache Manager for MyBatis
 * manages all Secondary Caches in MyBatis
 * 
 * 
 * MyBatis 全局二级缓存管理器
 * 负责管理MyBatis内部的所有二级缓存Cache
 * 该对象应该是 单例模式，一个MyBatis应用上下文中应该只有一个实例对象
 * 
 * 该管理器维护着MyBatis所有的查询所产生的CacheKey集合，当有update 操作执行时，会根据此update操作对应的StatementId，查看此StatementId是否指定了要刷新的查询缓存，然后指定此
 * 
 * 
 */
public interface RedisCachingManager {
	
	
	public boolean isInitialized();
	
	/**
	 * MyBatis是否开启了二级缓存，即 <setting name="cacheEnabled" value="true"/> 
	 * @return
	 */
	public boolean isCacheEnabled();

	/**
	 * 
	 * 初始化 缓存管理器，应该只被调用一次;
	 * 
	 * @param properties
	 * 	properties 中至少包含两个属性：
	 * 		dependency 	: 该值表示着缓存依赖配置文件的位置
	 * 		cacheEnbled	: "true" or "false",该配置必须要与<setting name="cacheEnabled">的值保持一致
	 */
	public void initialize(Properties properties);
	
	
	/**
	 * 整个插件的核心，根据指定的statementId更新与之关联的二级缓存
	 * 传入的StatementId集合是由会话级别的update语句对应的StatementId，
	 * EnhancedCachingManager将通过查询相应的StatementId去查看是否配置了依赖关系，如果有，则将依赖关系中的statementId的查询缓存全部清空
	 * @param set
	 */
	public void clearRelatedCaches(Set<String> set);
	
	

}

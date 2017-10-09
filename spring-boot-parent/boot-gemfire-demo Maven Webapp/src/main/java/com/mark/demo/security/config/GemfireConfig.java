package com.mark.demo.security.config;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.config.annotation.CacheServerConfigurer;

import com.mark.demo.security.utils.SpringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年10月8日
*
*/
@Configuration
public class GemfireConfig {
	  @Bean
	  public CacheServerConfigurer cacheServerPortConfigurer() {

	      return (beanName, cacheServerFactoryBean) -> {
	          cacheServerFactoryBean.setBindAddress("localhost");
	          cacheServerFactoryBean.setPort(40012);
	      };
	  }
	  
	  @Bean
	  public GemfireCacheManager cacheManager(GemFireCache gemfireCache) {
	      GemfireCacheManager cacheManager = new GemfireCacheManager();
	      cacheManager.setCache(gemfireCache);
	      return cacheManager;
	  }
	  
	  @Bean("gemfireCache")
	  public CacheFactoryBean gemfireCache() {
	    CacheFactoryBean gemfireCache = new CacheFactoryBean();
	    gemfireCache.setClose(true);
	    gemfireCache.setCopyOnRead(true);
	    return gemfireCache;
	  }
	  
	  @Bean
	  public PdxSerializer compositePdxSerializer() {
	      return new ReflectionBasedAutoSerializer();
	  }
	  
	 /* @Bean
	  public JtaTransactionManager transactionManager(UserTransaction userTransaction) {
	     JtaTransactionManager transactionManager = new JtaTransactionManager();
	     transactionManager.setUserTransaction(userTransaction);
	     return transactionManager;
	  }*/
	  
	  @Bean("userGemfireTemplate")
	  public GemfireTemplate userGemfireTemplate(){
		  GemfireTemplate template=new GemfireTemplate();
		  template.setRegion(SpringUtils.getBean("user"));
		  return template;
	  }
	  
	  @Bean("resourceGemfireTemplate")
	  public GemfireTemplate resourceGemfireTemplate(){
		  GemfireTemplate template=new GemfireTemplate();
		  template.setRegion(SpringUtils.getBean("resource"));
		  return template;
	  }
	  
	  @Bean("menuGemfireTemplate")
	  public GemfireTemplate menuGemfireTemplate(){
		  GemfireTemplate template=new GemfireTemplate();
		  template.setRegion(SpringUtils.getBean("menu"));
		  return template;
	  }
}

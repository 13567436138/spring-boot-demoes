package com.mark.demo.security.config;

import javax.transaction.UserTransaction;

import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.CacheServerConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;

/*
*hxp(hxpwangyi@126.com)
*2017年10月8日
*
*/
@Configuration
public class GemfireConfig {
	  @Bean
	  CacheServerConfigurer cacheServerPortConfigurer() {

	      return (beanName, cacheServerFactoryBean) -> {
	          cacheServerFactoryBean.setBindAddress("localhost");
	          cacheServerFactoryBean.setPort(40011);
	      };
	  }
	  
	  @Bean
	  PdxSerializer compositePdxSerializer() {
	      return new ReflectionBasedAutoSerializer();
	  }
	  
	 /* @Bean
	  public JtaTransactionManager transactionManager(UserTransaction userTransaction) {
	     JtaTransactionManager transactionManager = new JtaTransactionManager();
	     transactionManager.setUserTransaction(userTransaction);
	     return transactionManager;
	  }*/
}

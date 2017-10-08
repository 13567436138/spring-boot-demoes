package com.mark.demo.security.config;

import javax.transaction.UserTransaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.CacheServerConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.gemstone.gemfire.pdx.PdxSerializer;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;

/*
*hxp(hxpwangyi@126.com)
*2017年10月8日
*
*/
@Configuration
public class GemfireConfig {
	  @Bean
	  CacheServerConfigurer cacheServerPortConfigurer(String cacheServerHost,int cacheServerPort) {

	      return (beanName, cacheServerFactoryBean) -> {
	          cacheServerFactoryBean.setBindAddress(cacheServerHost);
	          cacheServerFactoryBean.setPort(cacheServerPort);
	      };
	  }
	  
	  @Bean
	  PdxSerializer compositePdxSerializer() {
	      return new ReflectionBasedAutoSerializer();
	  }
	  
	  @Bean
	  public JtaTransactionManager transactionManager(UserTransaction userTransaction) {
	     JtaTransactionManager transactionManager = new JtaTransactionManager();
	     transactionManager.setUserTransaction(userTransaction);
	     return transactionManager;
	  }
}

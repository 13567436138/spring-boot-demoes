package com.mark.demo.shiro_memched.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mark.demo.shiro_memched.session.MemcachedSessionManager;
import com.mark.demo.shiro_memched.utils.MemcachedUtils;
import com.mark.demo.shiro_memched.utils.SpringUtils;

import net.rubyeye.xmemcached.XMemcachedClient;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@Configuration
public class UtilConfig {

	
	@Bean(name="springUtils")
	public SpringUtils springUtils(ApplicationContext applicationContext){
		SpringUtils springUtils=new SpringUtils();
		springUtils.setApplicationContext(applicationContext);
		return springUtils;
	}
	
	@Bean
	public MemcachedSessionManager redisSessionManager(){
		MemcachedSessionManager manager=new MemcachedSessionManager();
		return manager;
	} 
	
	@Bean
	public MemcachedUtils memcachedUtil(){
		MemcachedUtils memcachedUtils=new MemcachedUtils();
		memcachedUtils.setClient(SpringUtils.getBean("xmemcachedClient"));
		return memcachedUtils;
	}
}

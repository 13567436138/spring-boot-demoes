package com.mark.demo.shiro.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mark.demo.shiro.session.RedisSessionManager;
import com.mark.demo.shiro.utils.SpringUtils;

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
	public RedisSessionManager redisSessionManager(){
		RedisSessionManager manager=new RedisSessionManager();
		return manager;
	} 
}

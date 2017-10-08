package com.mark.demo.security.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.mark.demo.security.session.RedisSessionManager;
import com.mark.demo.security.utils.JedisUtils;
import com.mark.demo.security.utils.SpringUtils;

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
	
	@Bean
	public JedisUtils jedisUtil(RedisTemplate<String, Object> redisTemplate){
		JedisUtils jedisUtil=new JedisUtils();
		jedisUtil.setRedisTemplate(redisTemplate);
		return jedisUtil;
	}
}

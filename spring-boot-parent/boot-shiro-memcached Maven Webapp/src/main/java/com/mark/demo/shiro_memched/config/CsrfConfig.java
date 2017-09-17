package com.mark.demo.shiro_memched.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mark.demo.shiro_memched.interceptor.CSRFTokenManager;
import com.mark.demo.shiro_memched.session.MemcachedSessionManager;
import com.mark.demo.shiro_memched.interceptor.CSRFTokenManager;

/*
*hxp(hxpwangyi@126.com)
*2017年9月17日
*
*/
@Configuration
public class CsrfConfig {

	@Bean
	public CSRFTokenManager cSRFTokenManager(MemcachedSessionManager memcachedSessionManager){
		CSRFTokenManager manager=new CSRFTokenManager();
		manager.setMemcachedSessionManager(memcachedSessionManager);
		return manager;
	}
}

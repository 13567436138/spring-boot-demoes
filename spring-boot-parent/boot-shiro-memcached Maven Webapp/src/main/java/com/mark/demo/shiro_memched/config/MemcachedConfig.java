package com.mark.demo.shiro_memched.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.rubyeye.xmemcached.buffer.SimpleBufferAllocator;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean;

/*
*hxp(hxpwangyi@126.com)
*2017年9月17日
*
*/
@Configuration
public class MemcachedConfig {
	@Value("${spring.datasource.url}")
	private String servers;
	
	@Bean("xmemcachedClient")
	public XMemcachedClientFactoryBean xmemcachedClientFactoryBean(){
		XMemcachedClientFactoryBean bean=new XMemcachedClientFactoryBean();
		bean.setServers(servers);
		List<Integer> weights=new ArrayList<Integer>();
		weights.add(1);
		bean.setWeights(weights);
		bean.setConnectionPoolSize(20);
		bean.setSessionLocator(new KetamaMemcachedSessionLocator());
		bean.setTranscoder(new SerializingTranscoder());
		bean.setBufferAllocator(new SimpleBufferAllocator());
		return bean;
	}
}

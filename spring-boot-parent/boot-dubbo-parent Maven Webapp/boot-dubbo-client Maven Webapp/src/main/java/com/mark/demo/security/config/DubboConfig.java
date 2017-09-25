package com.mark.demo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/*
*hxp(hxpwangyi@126.com)
*2017年9月25日
*
*/
@Configuration  
@PropertySource("classpath:dubbo.properties")  
@ImportResource({"classpath:spring-consumer.xml"}) 
public class DubboConfig {

}

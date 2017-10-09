package com.mark.demo.security.config;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
*hxp(hxpwangyi@126.com)
*2017年10月9日
*
*/
@Configuration
public class MyJerseryConfig extends ResourceConfig{
	public MyJerseryConfig(){
		packages("com.mark.demo.security.controller");
		Map<String, Object> pro = new HashMap<String, Object>(1);
        //模板编码
        pro.put("jersey.config.server.mvc.encoding.freemarker", "UTF-8");
        //指定模板基础路径
        pro.put("jersey.config.server.mvc.templateBasePath.freemarker", "/WEB-INF/ftl");

        addProperties(pro).register(FreemarkerMvcFeature.class);
	}
	
	@Bean
    public ServletRegistrationBean jerseyServlet() {
      ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
       registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MyJerseryConfig.class.getName());
       return registration;
    }
} 

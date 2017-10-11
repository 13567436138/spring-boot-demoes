package com.mark.demo.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

/*
*hxp(hxpwangyi@126.com)
*2017年10月11日
*
*/
@Configuration
public class WebAppRootContext implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	servletContext.setInitParameter("quartz:config-file", "quartz.properties");
    	servletContext.setInitParameter("quartz:start-on-load", "true");
    } 
}

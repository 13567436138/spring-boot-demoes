package com.mark.demo.security.security;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);  
  
    @Override  
    public void onLogoutSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {     
        logger.info("日志：ip:"+request.getRemoteAddr() +"host:"+request.getRemoteHost()+"退出时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));    
          
        super.onLogoutSuccess(request, response, authentication);  
      
          
    }  
}

package com.mark.demo.security.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomSavedRequestAwareAuthenticationSuccessHandler.class);  
     
	@Override  
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws ServletException, IOException {  
    	UserDetails userDetails = (UserDetails)authentication.getPrincipal(); 
    	String userName = userDetails.getUsername(); //用户名  
        String address =  request.getRemoteAddr();  //远程地址  
        //写入日志   
        logger.info("用户" + userName + "在地址" + address + "登入系统，时间："+new Date());  
        super.onAuthenticationSuccess(request, response, authentication);     
  
    }  
  
    @Override  
    public void setRequestCache(RequestCache requestCache) {  
          
        super.setRequestCache(requestCache);  
    }  
}

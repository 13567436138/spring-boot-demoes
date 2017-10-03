package com.mark.demo.security.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Service("customFilterSecurityInterceptor")  
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	@Resource    
    @Qualifier("customFilterInvocationSecurityMetadataSource")  
    private FilterInvocationSecurityMetadataSource securityMetadataSource;  
     
    @Resource    
    @Qualifier("customAccessDecisionManager")  
    @Override  
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager){  
        super.setAccessDecisionManager(accessDecisionManager);  
    }  

    @Resource    
    @Qualifier("authenticationManager")    
    @Override  
    public void setAuthenticationManager(AuthenticationManager newManager) {  
        super.setAuthenticationManager(newManager);  
    }  
      
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
          
        HttpServletRequest httpRequest = (HttpServletRequest)request;     
        HttpServletResponse httpResponse = (HttpServletResponse)response;  
        
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        
        if(auth==null){
        	httpRequest.getRequestDispatcher("/common/login").forward(httpRequest, httpResponse);
        	return;
        }
        
        Object principal=auth.getPrincipal();
        if(principal==null){
        	httpRequest.getRequestDispatcher("/common/login").forward(httpRequest, httpResponse);
        	return;
        }
               
       FilterInvocation fi = new FilterInvocation(request, response, chain);     
       invoke(fi);     
  
    }  
      
    /**  
     *   
     * @param fi   
     * @throws ServletException   
     * @throws IOException   
     */  
    private void invoke(FilterInvocation fi) throws IOException, ServletException {  
          
        InterceptorStatusToken token = null;     
        try {     
              token = super.beforeInvocation(fi);  
                
        } catch (Exception e) {     
            if( e instanceof AccessDeniedException){     
                throw new AccessDeniedException("用户无权访问");    
            }  
             return;  
         }     
                   
          try {     
               fi.getChain().doFilter(fi.getRequest(), fi.getResponse());     
          } finally {     
               super.afterInvocation(token, null);     
          }     
  
          
    }  
  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
          
  
    }  
  
    @Override  
    public Class<? extends Object> getSecureObjectClass() {  
          
        return FilterInvocation.class;    
    }  
  
    @Override  
    public SecurityMetadataSource obtainSecurityMetadataSource() {  
          
        return this.securityMetadataSource;  
    }  
      
    @Override  
    public void destroy() {  
          
  
    }  
}

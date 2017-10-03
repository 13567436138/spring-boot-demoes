package com.mark.demo.security.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
public class CustomAccessDecisionManager implements AccessDecisionManager {

		/**  
	    * 该方法：需要比较权限和权限配置  
	    * object参数是一个 URL, 同一个过滤器该url对应的权限配置被传递过来.  
	    * 查看authentication是否存在权限在configAttributes中  
	    * 如果没有匹配的权限, 扔出一个拒绝访问的异常  
	    */  
	    @Override  
	    public void decide(Authentication authentication, Object object,  
	            Collection<ConfigAttribute> configAttributes)  
	            throws AccessDeniedException, InsufficientAuthenticationException {  
	        if (configAttributes==null){  
	            return;  
	        }          
	        Iterator<ConfigAttribute> iter = configAttributes.iterator();          
	        while(iter.hasNext()){  
	            ConfigAttribute ca = iter.next();  
	            String needRole = ca.getAttribute();
	            //gra 为用户所被赋予的权限，needRole为访问相应的资源应具有的权限  
	            if(needRole!=null){
		            for (GrantedAuthority gra : authentication.getAuthorities()) {  
		                if (needRole.trim().equals(gra.getAuthority().trim())) {  
		                    return;  
		                }  
		            }  
	            }else{
	            	return;
	            }
	        }  
	        throw new AccessDeniedException("Access Denied");  
	  
	  
	    }  
	  
	    @Override  
	    public boolean supports(ConfigAttribute attribute) {  
	          
	        return true;  
	    }  
	  
	    @Override  
	    public boolean supports(Class<?> clazz) {  
	          
	        return true;  
	    }  

}

package com.mark.demo.security.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.mark.demo.security.entity.Resource;
import com.mark.demo.security.service.ResourceService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Service("customFilterInvocationSecurityMetadataSource") 
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Autowired
	private ResourceService resourceService;
	private static HashMap<String, Collection<ConfigAttribute>> resourceMap = null;   
 
    @PostConstruct        
    public void init() {    
        loadResourceDefine();    
    }  

    private void loadResourceDefine() {     
        Iterable<Resource> authorities = resourceService.findAll();
                      
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
         
        if(authorities!=null && authorities.iterator().hasNext() )  
            for (Resource auth : authorities) {      
                String authName = auth.getRole();       
                ConfigAttribute ca = new SecurityConfig(authName);                        
                String url = auth.getUrl();                                    
                if (resourceMap.containsKey(url)) { 
                    Collection<ConfigAttribute> value = resourceMap.get(url);  
                    value.add(ca);  
                    resourceMap.put(url, value);  
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
                    atts.add(ca);  
                    resourceMap.put(url, atts);  
                }  
            }  
  
    }  
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		 return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation)object).getRequestUrl();       
        int firstQuestionMarkIndex = url.indexOf("?");  
        if (firstQuestionMarkIndex != -1) {  
            url = url.substring(0, firstQuestionMarkIndex);  
        }  
        Iterator<String> iter = resourceMap.keySet().iterator();    
          
        String matchUrl=null;
        while (iter.hasNext()) {  
            String resURL = iter.next();   
              if(url.startsWith(resURL)){  
                  if(matchUrl==null||matchUrl.length()<resURL.length())  
                      matchUrl=resURL;  
              }                
                  
        }  
        if(matchUrl!=null){    
            return resourceMap.get(matchUrl);  
        }  
        return null; 
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}

package com.mark.demo.shiro_memched.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mark.demo.shiro_memched.listener.ShiroSessionListener;
import com.mark.demo.shiro_memched.security.MysqlRealm;
import com.mark.demo.shiro_memched.security.UserCredentialsMatcher;
import com.mark.demo.shiro_memched.security.cache.MemcachedCacheManager;
import com.mark.demo.shiro_memched.security.filter.AuthenticationFilter;
import com.mark.demo.shiro_memched.security.filter.SimpleExecutiveFilter;
import com.mark.demo.shiro_memched.security.session.MemcachedSessionDAO;
import com.mark.demo.shiro_memched.security.utils.IdGen;
import com.mark.demo.shiro_memched.utils.SpringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@Configuration
public class ShiroConfiguration {
	@Value("${cookie.domain}")  
    private String rememberCookieDomain;  
      
    @Value("${cookie.rememberName}")  
    private String rememberCookieName;  
      
    @Value("${cookie.path}")  
    private String rememberCookiePath;  
    
    @Value("${cookie.maxage}")
    private String rememberCookieMaxAge;
    
    @Autowired
    private SpringUtils springUtils;
	
	/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/js/**", "anon,exec");
        filterChainDefinitionMap.put("/css/**", "anon,exec");
        filterChainDefinitionMap.put("/common/login/**", "anon,exec");
        filterChainDefinitionMap.put("/menu/**", "authc,exec");
        filterChainDefinitionMap.put("/common/**", "authc,exec");
        filterChainDefinitionMap.put("/admins/**", "authc,exec");
        
        shiroFilterFactoryBean.setLoginUrl("/common/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        shiroFilterFactoryBean.setSuccessUrl("admins/indexed/index");
        
        Map<String,Filter> filterMap=new HashMap<String,Filter>();
        filterMap.put("exec", new SimpleExecutiveFilter());
        filterMap.put("authc", new AuthenticationFilter());
        filterMap.put("roles", new RolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        return shiroFilterFactoryBean;
    }

    @Bean
    public org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        MysqlRealm myRealm = new MysqlRealm();
        myRealm.setCredentialsMatcher(new UserCredentialsMatcher());
        myRealm.setSessionDAO(sessionDao());
        myRealm.setUserMapper(springUtils.getBean("userMapper"));
        
        securityManager.setRealm(myRealm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        
        CookieRememberMeManager rememberMeManager=new CookieRememberMeManager();
        rememberMeManager.setCipherKey("#{T(org.apache.shiro.codec.Base64).decode('4AvVhmFLUs0KTA3Kprsdag==')}".getBytes());
        rememberMeManager.setCookie(rememberCookie());
        
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }
    
    @Bean
    public SimpleCookie rememberCookie(){
    	SimpleCookie cookie=new SimpleCookie();
    	cookie.setDomain(rememberCookieDomain);
    	cookie.setName(rememberCookieName);
    	cookie.setPath(rememberCookiePath);
    	cookie.setMaxAge(Integer.parseInt(rememberCookieMaxAge));
    	cookie.setHttpOnly(true);
    	return cookie;
    }

    @Bean
    public MemcachedSessionDAO sessionDao(){
    	MemcachedSessionDAO sessionDao=new MemcachedSessionDAO();
        sessionDao.setSessionIdGenerator(new IdGen());
        return  sessionDao;
    }
    
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        
        sessionManager.setSessionDAO(sessionDao());
        
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setSessionValidationInterval(120000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        
        return sessionManager;
    }

    @Bean(name="redisCacheManager")
    public MemcachedCacheManager cacheManager() {
    	MemcachedCacheManager redisCacheManager = new MemcachedCacheManager();
        return redisCacheManager;
    }

}

package com.mark.demo.shiro_memched.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mark.demo.shiro_memched.security.utils.IdGen;
import com.mark.demo.shiro_memched.session.MemcachedSessionManager;

public final class CSRFTokenManager
{
    private static final Logger LOGGER     = Logger.getLogger(CSRFTokenManager.class);
    
    /**
     * The token parameter name
     */
    public static final String  CSRF_TOKEN_NAME = "csrfToken";
    
    private MemcachedSessionManager memcachedSessionManager;
    
    public void setMemcachedSessionManager(MemcachedSessionManager memcachedSessionManager)
    {
        this.memcachedSessionManager = memcachedSessionManager;
    }
    
    public String getTokenForRequest(HttpServletRequest request)
    {
        String token;
        synchronized (request)
        {
            token = memcachedSessionManager.getString(request, MemcachedSessionManager.SessionKey.CSRF_KEY);
            if (StringUtils.isNotBlank(token))
            {// 更新会话时间
                memcachedSessionManager.expire(request, MemcachedSessionManager.SessionKey.CSRF_KEY);
            }
            else
            {
                token = IdGen.uuid();
                LOGGER.info("查找不到 csrf token, 重新生成; token:" + token);
                memcachedSessionManager.put(request, MemcachedSessionManager.SessionKey.CSRF_KEY, token);
            }
        }
        return token;
    }
    
    /**
     * 移出CSRF TOKEN_NAME
     * @param request
     */
    public void removeTokenForRequest(HttpServletRequest request)
    {
        MemcachedSessionManager.remove(request, MemcachedSessionManager.SessionKey.CSRF_KEY);
    }
    
    /**
     * Extracts the token value from the session
     * 
     * @param request
     * @return
     */
    public static String getTokenFromRequest(HttpServletRequest request)
    {
        String token = request.getHeader(CSRF_TOKEN_NAME);
        if (StringUtils.isBlank(token)) token = request.getParameter(CSRF_TOKEN_NAME);
        return token;
    }
}

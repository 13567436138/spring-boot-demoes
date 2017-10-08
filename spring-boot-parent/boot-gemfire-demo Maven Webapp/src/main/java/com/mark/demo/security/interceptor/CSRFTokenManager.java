package com.mark.demo.security.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mark.demo.security.session.RedisSessionManager;
import com.mark.demo.security.utils.IdGen;

public final class CSRFTokenManager
{
    private static final Logger LOGGER     = Logger.getLogger(CSRFTokenManager.class);
    
    /**
     * The token parameter name
     */
    public static final String  CSRF_TOKEN_NAME = "csrfToken";
    
    private RedisSessionManager redisSessionManager;
    
    public void setRedisSessionManager(RedisSessionManager redisSessionManager)
    {
        this.redisSessionManager = redisSessionManager;
    }
    
    public String getTokenForRequest(HttpServletRequest request)
    {
        String token;
        synchronized (request)
        {
            token = redisSessionManager.getString(request, RedisSessionManager.SessionKey.CSRF_KEY);
            if (StringUtils.isNotBlank(token))
            {// 更新会话时间
                redisSessionManager.expire(request, RedisSessionManager.SessionKey.CSRF_KEY);
            }
            else
            {
                token = IdGen.uuid();
                LOGGER.info("查找不到 csrf token, 重新生成; token:" + token);
                redisSessionManager.put(request, RedisSessionManager.SessionKey.CSRF_KEY, token);
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
        RedisSessionManager.remove(request, RedisSessionManager.SessionKey.CSRF_KEY);
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

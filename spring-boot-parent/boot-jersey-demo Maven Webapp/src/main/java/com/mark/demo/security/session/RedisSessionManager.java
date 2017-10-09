package com.mark.demo.security.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mark.demo.security.filter.SessionFilter;
import com.mark.demo.security.utils.CookieUtils;
import com.mark.demo.security.utils.JedisUtils;

public class RedisSessionManager
{
    public static final Logger   logger               = LoggerFactory.getLogger(RedisSessionManager.class);
    
    private static final Integer defaultExpireSeconds = 60 * 30;                                           // 30分钟
    
    /**
     * 将对象放入session缓存
     * @param request
     * @param key
     * @param value
     * @return
     */
    public boolean put(HttpServletRequest request, SessionKey key, Object value)
    {
        return put(request, key, value, defaultExpireSeconds);
    }
    
    /**
     * 将对象放入session缓存
     * @param request
     * @param key
     * @param value
     * @param expireSecond
     * @return
     */
    public boolean put(HttpServletRequest request, SessionKey key, Object value, int expireSecond)
    {
        String realKey = getRealKey(request, key);
        if (StringUtils.isNotBlank(realKey))
        {
            JedisUtils.setObject(realKey, value, expireSecond);
            return true;
        }
        return false;
    }
    
    /**
     * 清除session中的指定值
     * @param request
     * @param key
     * @return
     */
    public static boolean remove(HttpServletRequest request, SessionKey key)
    {
        String realKey = getRealKey(request, key);
        if (StringUtils.isNotBlank(realKey))
        {
            JedisUtils.del(realKey);
            return true;
        }
        return false;
    }
    
    /**
     * 从session缓存获取对象
     * @param request
     * @param key
     * @return
     */
    public Object getObject(HttpServletRequest request, SessionKey key)
    {
        String realKey = getRealKey(request, key);
        if (StringUtils.isNotBlank(realKey)) { return JedisUtils.getObject(realKey); }
        return null;
    }
    
    /**
     * 更新过期的时间
     * @param request
     * @param key
     */
    public void expire(HttpServletRequest request, SessionKey key)
    {
        String realKey = getRealKey(request, key);
        if (StringUtils.isNotBlank(realKey))
        {
            JedisUtils.expire(realKey, defaultExpireSeconds);
        }
    }
    
    public String getString(HttpServletRequest request, SessionKey key)
    {
        return (String) getObject(request, key);
    }
    
    /**
     * 构造具体的KEY
     * @param request
     * @param key
     * @return
     */
    static String getRealKey(HttpServletRequest request, SessionKey key)
    {
        String sessionId = getSessionId(request);
        if (StringUtils.isBlank(sessionId)) return null;
        return new StringBuilder(key.getValue()).append("_").append(sessionId).toString();
    }
    
    /**
     * 清空指定session的所有缓存, 并清除随机生成的sessionID
     * @param request
     */
    public static void clear(HttpServletRequest request, HttpServletResponse response)
    {
        SessionKey[] keys = SessionKey.values();
        for (SessionKey key : keys)
        {
            remove(request, key);
        }
        CookieUtils.remove(request, response, SessionFilter.SESSION_ID_NAME);
    }
    
    /**
     * 取SESSION ID
     * @param request
     * @return
     */
    public static final String getSessionId(HttpServletRequest request)
    {
        String sessionId = CookieUtils.get(request, SessionFilter.SESSION_ID_NAME);
        if (StringUtils.isBlank(sessionId))
        {
            sessionId = (String) request.getAttribute(SessionFilter.SESSION_ID_NAME);
        }
        return sessionId;
    }
    
    /**
     * 使用这给类维护key值，方便清理memcache中的指定session
     */
    public enum SessionKey
    {
        // CSRF key
        CSRF_KEY("csrfToken"),
        // 验证码
        CAPTCHA("captcha"),
        // 是否显示验证码
        SHOW_CAPTCHA("showCaptcha");
        private String value;
        
        SessionKey(String value)
        {
            this.value = value;
        }
        
        public String getValue()
        {
            return value;
        }
    }
}

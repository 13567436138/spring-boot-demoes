package com.mark.demo.security.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mark.demo.security.utils.CookieUtils;

public class SessionFilter extends OncePerRequestFilter
{
    public static final String SESSION_ID_NAME = "JSESSIONID";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        synchronized (request)
        {
            String sessionId = CookieUtils.get(request, SESSION_ID_NAME);
            if (StringUtils.isBlank(sessionId))
            {
                sessionId = (String) request.getAttribute(SESSION_ID_NAME);
                if (StringUtils.isBlank(sessionId))
                {
                    sessionId =buildPrimaryKey();
                    CookieUtils.put(request, response, SESSION_ID_NAME, sessionId);
                    request.setAttribute(SESSION_ID_NAME, sessionId);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    
    @Override
    public void destroy()
    {
    }
    
    public static String buildPrimaryKey()
    {
        String uuid = UUID.randomUUID().toString();
        return StringUtils.replace(uuid, "-", "").toUpperCase();
    }

}

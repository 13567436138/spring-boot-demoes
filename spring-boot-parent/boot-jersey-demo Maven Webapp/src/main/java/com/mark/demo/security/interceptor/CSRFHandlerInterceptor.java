package com.mark.demo.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CSRFHandlerInterceptor extends HandlerInterceptorAdapter
{
    private CSRFTokenManager csrfTokenManager;
    
    public void setCsrfTokenManager(CSRFTokenManager csrfTokenManager)
    {
        this.csrfTokenManager = csrfTokenManager;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // 只拦截POST请求
        if (request.getMethod().equalsIgnoreCase("POST"))
        {
            // 检测 CSRF 令牌
            String sessionToken = csrfTokenManager.getTokenForRequest(request);
            String requestToken = csrfTokenManager.getTokenFromRequest(request);
            if (StringUtils.equals(sessionToken, requestToken))
            {
                return true;
            }
            else
            {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}

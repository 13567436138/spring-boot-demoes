package com.mark.demo.shiro.security.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSON;
import com.mark.demo.shiro.constant.CharsetConst;
import com.mark.demo.shiro.entity.JsonMessage;
import com.mark.demo.shiro.entity.User;
import com.mark.demo.shiro.session.RedisSessionManager;
import com.mark.demo.shiro.utils.IPUtil;
import com.mark.demo.shiro.utils.StringUtils;


public class AuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter
{
    private String captchaParam = "validateCode";
    
    private String messageParam = "message";
    
    public AuthenticationFilter()
    {
        super();
    }
    
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
    {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null)
        {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
    }

    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        if (isLoginRequest(request, response))
        { // 登陆状态下
            if (isLoginSubmission(request, response))
            {
                return executeLogin(request, response);
            }
            else
            {
                return true;
            }
        }
        else
        { // 未登陆状态
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if ("XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With")))
            {// ajax请求
                httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
                httpResponse.setCharacterEncoding(CharsetConst.CHARSET_UT);
                JsonMessage message = new JsonMessage(403,"用户没登入");
                PrintWriter outPrintWriter = httpResponse.getWriter();
                outPrintWriter.println(JSON.toJSON(message));
                outPrintWriter.flush();
                outPrintWriter.close();
                return false;
            }
            else
            {// http 请求
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }
    }
    
    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response)
    {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className) || UnknownAccountException.class.getName().equals(className))
        {
            message = "用户或密码错误, 请重试.";
        }
        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:"))
        {
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        }
        else
        {
            message = "系统出现点问题，请稍后再试！";
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute("message", message);
        return true;
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
    {
        Subject subject = getSubject(request, response);
        // 如果 isAuthenticated 为 false 证明不是登录过的，
        // 同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
        if (!subject.isAuthenticated() && subject.isRemembered())
        {

            Object object = subject.getPrincipal();
            if (null != object)
            {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                User userInfo = (User) object;
                if(StringUtils.isBlank(userInfo.getPassword())){//表明这是之前的用户COOKIE
                    // 清理自定义会话
                    RedisSessionManager.clear(httpRequest, httpResponse);
                    // 清理结算平台 cookie;
                    //CookieUtils.remove(httpRequest, httpResponse, CookieConst.PAYMENT);
                    Subject currentUser = SecurityUtils.getSubject();
                    if (null != currentUser) currentUser.logout();
                    return subject.isAuthenticated();
                }
                UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword().toCharArray(), true,
                        IPUtil.getOriginalIpAddr(httpRequest));
                subject.login(token);
            }
        }
        return subject.isAuthenticated();
    }
    
    
}
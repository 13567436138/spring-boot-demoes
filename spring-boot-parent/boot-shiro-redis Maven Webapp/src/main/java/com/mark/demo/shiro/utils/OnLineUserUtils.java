package com.mark.demo.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mark.demo.shiro.constant.CommonConst;
import com.mark.demo.shiro.entity.User;
import com.mark.demo.shiro.exception.BusinessException;


public class OnLineUserUtils
{
    private static Logger logger = LoggerFactory.getLogger(OnLineUserUtils.class);
    
    
    /**
     * 获取当前登录者对象
     */
    public static User getPrincipal()
    {
        try
        {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getPrincipal();
            if (null != object) { return (User) object; }
        }
        catch (UnavailableSecurityManagerException e)
        {
            logger.error(e.getLocalizedMessage());
        }
        catch (InvalidSessionException e)
        {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * 获取授权主要对象
     */
    public static Subject getSubject()
    {
        return SecurityUtils.getSubject();
    }
    
    public static Session getSession()
    {
        try
        {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null)
            {
                session = subject.getSession();
            }
            if (session != null) { return session; }
        }
        catch (InvalidSessionException e)
        {
        }
        return null;
    }
}

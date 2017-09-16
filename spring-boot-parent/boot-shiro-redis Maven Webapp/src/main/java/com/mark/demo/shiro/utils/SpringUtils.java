package com.mark.demo.shiro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware, DisposableBean
{
    private static final Logger logger = LoggerFactory.getLogger(SpringUtils.class);
    
    public SpringUtils()
    {
    }
    
    private static ApplicationContext applicationContext = null;
    
    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public  ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    
    @Override
    public void destroy() throws Exception
    {
        clearHolder();
    }
    
    /**
     * 根据类型名称取得bean
     * @param name 类型名称
     * @return T bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name)
    {
        return (T) applicationContext.getBean(name);
    }
    
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param requiredType 类型
     * @return T bean
     */
    public static <T> T getBean(Class<T> requiredType)
    {
        return applicationContext.getBean(requiredType);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext; 
    }
    
    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public  void clearHolder()
    {
         applicationContext = null;
    }
    
}

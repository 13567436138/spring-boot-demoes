package com.mark.demo.security.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


public class PropertiesLoader
{
    private static final Logger   logger         = LoggerFactory.getLogger(PropertiesLoader.class);
    
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    private final Properties      properties;
    
    public PropertiesLoader(String ... resourcesPaths)
    {
        properties = loadProperties(resourcesPaths);
    }
    
    public Properties getProperties()
    {
        return this.properties;
    }
    
    /**
     * 设置资源文件参数
     * @param fileName 资源文件
     * @param key 属性key
     * @param value 属性值
     */
    public void setProperty(String fileName, String key, String value)
    {
        if (null != properties)
        {
            OutputStream fos = null;
            try
            {
                URI uri = resourceLoader.getResource(fileName).getURI();
                System.out.println(uri);
                properties.setProperty(key, value);
                fos = new FileOutputStream(new File(uri));
                properties.store(fos, "Update '" + key + "' value");
            }
            catch (FileNotFoundException e)
            {
                LoggerUtils.logError(logger, e.getMessage());
            }
            catch (IOException e)
            {
                LoggerUtils.logError(logger, e.getMessage());
            }
            finally
            {
                IOUtils.closeQuietly(fos);
            }
        }
    }
    
    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     * @param key 属性key
     * @return String 属性值
     */
    private String getValue(String key)
    {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) { return systemProperty; }
        if (properties.containsKey(key)) { return properties.getProperty(key); }
        return "";
    }
    
    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     * @param key 属性key
     * @return String 属性值
     */
    public String getProperty(String key)
    {
        String value = getValue(key);
        if (value == null) { throw new NoSuchElementException(); }
        return value;
    }
    
    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     * @param key 属性key
     * @param defaultValue 默认值
     * @return String 属性值
     */
    public String getProperty(String key, String defaultValue)
    {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     * @param key 属性key
     * @return Integer 属性值
     */
    public Integer getInteger(String key)
    {
        String value = getValue(key);
        if (value == null) { throw new NoSuchElementException(); }
        return Integer.valueOf(value);
    }
    
    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Integer 属性值
     */
    public Integer getInteger(String key, Integer defaultValue)
    {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }
    
    /**
     * 取出Long类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     * @param key 属性key
     * @return Long 属性值
     */
    public Long getLong(String key)
    {
        String value = getValue(key);
        if (value == null) { throw new NoSuchElementException(); }
        return Long.valueOf(value);
    }
    
    /**
     * 取出Long类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Long 属性值
     */
    public Long getLong(String key, Long defaultValue)
    {
        String value = getValue(key);
        return value != null ? Long.valueOf(value) : defaultValue;
    }
    
    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     * @param key 属性key
     * @return Double 属性值
     */
    public Double getDouble(String key)
    {
        String value = getValue(key);
        if (value == null) { throw new NoSuchElementException(); }
        return Double.valueOf(value);
    }
    
    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Double 属性值
     */
    public Double getDouble(String key, Integer defaultValue)
    {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }
    
    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     * @param key 属性key
     * @return Boolean 属性值
     */
    public Boolean getBoolean(String key)
    {
        String value = getValue(key);
        if (value == null) { throw new NoSuchElementException(); }
        return Boolean.valueOf(value);
    }
    
    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Boolean 属性值
     */
    public Boolean getBoolean(String key, boolean defaultValue)
    {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }
    
    /**
     * 根据文件路径获取属性集合
     * @param resourcesPaths 文件路径
     * @return Properties 属性集合
     */
    private Properties loadProperties(String ... resourcesPaths)
    {
        Properties props = new Properties();
        for (String location : resourcesPaths)
        {
            InputStream is = null;
            try
            {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
            }
            catch (IOException ex)
            {
                LoggerUtils.logError(logger, ex.getMessage());
            }
            finally
            {
                IOUtils.closeQuietly(is);
            }
        }
        return props;
    }
}
package com.mark.demo.security.exception;

import com.mark.demo.security.entity.EnumDescribable;

public class BusinessException extends Exception
{
    //
    private static final long serialVersionUID = -3267019434607947850L;
    
    private Integer           code;
    
    private Object            object;
    
    /**
     * 目前业务异常非常多，业务异常代码未进行整理，考虑到后期统一对业务异常代码进行规范编号的问题，目前开放此接口解决困扰程序员找不到特定的错误代码的问题
     *
     * @param message
     */
    public BusinessException(String message)
    {
        super(message);
    }
    
    public BusinessException(EnumDescribable enumDescribable){
    	this(enumDescribable.getCode(),enumDescribable.getMessage());
    }
    
    public BusinessException(Integer code, String message)
    {
        super(message);
        this.code = code;
    }
    
    public BusinessException(Integer code, String message, Object object)
    {
        this(code, message);
        this.object = object;
    }
    
   
    
    public Integer getCode()
    {
        return code;
    }
    
    public String getMessage()
    {
        return super.getMessage();
    }
    
    public Object getObject()
    {
        return object;
    }
    
    public void setObject(Object object)
    {
        this.object = object;
    }
    
  
}

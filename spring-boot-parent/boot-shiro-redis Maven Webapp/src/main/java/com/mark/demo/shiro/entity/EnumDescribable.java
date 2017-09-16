package com.mark.demo.shiro.entity;

import java.io.Serializable;


public interface EnumDescribable extends Serializable
{
    /**
     * 获取异常代码
     * @return
     * @author 夏铭
     */
    Integer getCode();
    
    /**
     * 获取异常代码描述
     * @return
     * @author 夏铭
     */
    String getMessage();
}

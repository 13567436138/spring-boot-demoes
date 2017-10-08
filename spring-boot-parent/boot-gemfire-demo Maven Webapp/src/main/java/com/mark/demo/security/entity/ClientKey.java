package com.mark.demo.security.entity;

public class ClientKey 
{
    private static final long serialVersionUID = 1L;
    
    /**
     * 接入类型
     * 001 交易平台
     * 002 文件服务器
     */
    private java.lang.String  accessType;
    
    /**认证钥匙*/
    private java.lang.String  userKey;
    
    /**白名单限制*/
    private java.lang.Boolean userLimit;
    
    /**申请时间*/
    private java.lang.Long    createTime;
    
    /**修改时间*/
    private java.lang.Long    updateTime;
    
    /**申请者IP*/
    private java.lang.Integer createIp;
    
    /**审核状态（0：未审核，1：审核通过，2：审核不通过)*/
    private java.lang.Short   checkState;
    
    public java.lang.String getAccessType()
    {
        return this.accessType;
    }
    
    public void setAccessType(java.lang.String accessType)
    {
        this.accessType = accessType;
    }
    
    public java.lang.String getUserKey()
    {
        return this.userKey;
    }
    
    public void setUserKey(java.lang.String userKey)
    {
        this.userKey = userKey;
    }
    
    public java.lang.Boolean getUserLimit()
    {
        return this.userLimit;
    }
    
    public void setUserLimit(java.lang.Boolean userLimit)
    {
        this.userLimit = userLimit;
    }
    
    public java.lang.Long getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(java.lang.Long createTime)
    {
        this.createTime = createTime;
    }
    
    public java.lang.Long getUpdateTime()
    {
        return this.updateTime;
    }
    
    public void setUpdateTime(java.lang.Long updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public java.lang.Integer getCreateIp()
    {
        return this.createIp;
    }
    
    public void setCreateIp(java.lang.Integer createIp)
    {
        this.createIp = createIp;
    }
    
    public java.lang.Short getCheckState()
    {
        return this.checkState;
    }
    
    public void setCheckState(java.lang.Short checkState)
    {
        this.checkState = checkState;
    }
}

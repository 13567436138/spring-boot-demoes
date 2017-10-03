package com.mark.demo.security.entity;

import java.io.Serializable;
import java.util.List;

public class JsonMessage implements Serializable
{
    //
    private static final long serialVersionUID = 714679657596837388L;
    
    public JsonMessage()
    {
    }
    
    public JsonMessage(int code ,String message)
    {
        this.code = code;
        this.message = message;
    }
    
    private Integer code;
    
    private String  message;
    
    private Object  object;
    
    private List<?> rows;
    
    private Long    total;
    
    // 分页查询时, 当前页码
    private Integer currentPage;
    
    // 分页查询时，总共页数
    private Integer totalPage;
    
    // 是否有下一页
    private Boolean hasNext;
    
    // 是否有上一页
    private Boolean hasPrevious;
    
    public List<?> getRows()
    {
        return rows;
    }
    
    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }
    
    public Long getTotal()
    {
        return total;
    }
    
    public void setTotal(Long total)
    {
        this.total = total;
    }
    
    public Integer getCode()
    {
        return code;
    }
    
    public void setCode(Integer code)
    {
        this.code = code;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public Object getObject()
    {
        return object;
    }
    
    public void setObject(Object object)
    {
        this.object = object;
    }
    
    public Integer getCurrentPage()
    {
        return currentPage;
    }
    
    public void setCurrentPage(Integer currentPage)
    {
        this.currentPage = currentPage;
    }
    
    public Integer getTotalPage()
    {
        return totalPage;
    }
    
    public void setTotalPage(Integer totalPage)
    {
        this.totalPage = totalPage;
    }
    
    public Boolean getHasNext()
    {
        return hasNext;
    }
    
    public void setHasNext(Boolean hasNext)
    {
        this.hasNext = hasNext;
    }
    
    public Boolean getHasPrevious()
    {
        return hasPrevious;
    }
    
    public void setHasPrevious(Boolean hasPrevious)
    {
        this.hasPrevious = hasPrevious;
    }
}
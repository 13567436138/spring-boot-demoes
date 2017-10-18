package com.mark.demo.security.utils;

import java.util.List;

import com.mark.demo.security.base.PaginateResult;
import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.entity.EnumDescribable;
import com.mark.demo.security.entity.JsonMessage;

public class JsonMessageUtils
{
    // 私有构造器
    private JsonMessageUtils()
    {
        super();
    }
    
    /**
     * API接口处理结果反馈：返回分页格式的JSON对象<code,message,rows,total>
     * @param errorCode 异常码EnumDescribable
     * @param result 结果集及Pagination对象
     * @return JsonMessage JsonMessage
     * @author 
     */
    public static <E> JsonMessage getJsonMessage(EnumDescribable errorCode, PaginateResult<E> result)
    {
        JsonMessage jsonMessage = getJsonMessage(errorCode);
        jsonMessage.setRows(result.getRows());
        jsonMessage.setTotal(result.getPage().getTotalCount());
        return jsonMessage;
    }
    
    /**
     * API接口处理结果反馈：返回接口处理状态码及状态描述<主要用于增、删、改等不需要返回数据的操作>
     * @param describable 异常代码描述EnumDescribable
     * @return JsonMessage JsonMessage
     * @author
     */
    public static JsonMessage getJsonMessage(EnumDescribable describable)
    {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setCode(describable.getCode());
        jsonMessage.setMessage(describable.getMessage());
        return jsonMessage;
    }
    
    /**
     * API接口处理结果反馈 ：返回接口处理状态码、状态描述及数据对象
     * @param describable 异常代码描述EnumDescribable
     * @param object 数据对象
     * @return JsonMessage JsonMessage
     * @author 
     */
    public static JsonMessage getJsonMessage(EnumDescribable describable, Object object)
    {
        JsonMessage jsonMessage = getJsonMessage(describable);
        jsonMessage.setObject(object);
        return jsonMessage;
    }
    
    /**
     * API接口处理结果反馈 ：返回接口处理状态码、状态描述及数据对象<可使用上一方法替代>
     * @param describable 异常代码描述EnumDescribable
     * @param list 结果集
     * @return JsonMessage JsonMessage
     * @author 
     */
    public static JsonMessage getJsonMessage(EnumDescribable describable, List<?> list)
    {
        JsonMessage jsonMessage = getJsonMessage(describable);
        jsonMessage.setRows(list);
        return jsonMessage;
    }
    /**
     * API接口处理结果反馈 ：返回接口处理状态码、状态描述及数据对象<可使用上一方法替代>
     * @param describable 异常代码描述EnumDescribable
     * @param list 结果集
     * @return JsonMessage JsonMessage
     * @author 
     */
    public static <E> JsonMessage getJsonMessage(EnumDescribable errorCode, Object obj,
            List<?> list)
    {
        JsonMessage jsonMessage = getJsonMessage(errorCode);
        jsonMessage.setRows(list);
        jsonMessage.setObject(obj);
        return jsonMessage;
    }
    
    /**
     * 
     * @param describable
     * @param obj
     * @param list
     * @return
     */
    public static <E> JsonMessage getJsonMessage(EnumDescribable errorCode, Object obj,
            PaginateResult<E> result)
    { 
        JsonMessage jsonMessage = getJsonMessage(errorCode);
        jsonMessage.setRows(result.getRows());
        jsonMessage.setTotal(result.getPage().getTotalCount());
        jsonMessage.setCurrentPage(result.getPage().getCurrentPage());
        jsonMessage.setHasNext(result.getPage().getHasNextPage());
        jsonMessage.setHasPrevious(result.getPage().getHasPreviousPage());
        jsonMessage.setTotalPage(result.getPage().getTotalPage());
        jsonMessage.setObject(obj);
        return jsonMessage;
    }
    
    /**
     * @param code code
     * @param message message
     * @return JsonMessage JsonMessage
     */
    public static JsonMessage getJsonMessage(EnumDescribable errorCode, List<?> list, Pagination page)
    {
        JsonMessage jsonMessage = getJsonMessage(errorCode);
         jsonMessage.setRows(list);
         jsonMessage.setTotal(page.getTotalCount());
         jsonMessage.setCurrentPage(page.getCurrentPage());
         jsonMessage.setHasNext(page.getHasNextPage());
         jsonMessage.setHasPrevious(page.getHasPreviousPage());
         jsonMessage.setTotalPage(page.getTotalPage());
        return jsonMessage;
    }
    
    /**
     * 单对象持有反馈：API接口返回处理结果及对象数据的JSON处理
     * @param code 异常码
     * @param message 异常集描述
     * @param object Objecct
     * @return JsonMessage JsonMessage
     */
    public static JsonMessage getJsonMessage(Integer code, String message, Object object)
    {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setCode(code);
        jsonMessage.setMessage(message);
        jsonMessage.setObject(object);
        return jsonMessage;
    }
    
    /**
     * 单对象持有反馈：API接口返回处理结果及对象数据的JSON处理
     * @param code 异常码
     * @param message 异常集描述
     * @param object Objecct
     * @return JsonMessage JsonMessage
     */
    public static JsonMessage getJsonMessage(Integer code, String message)
    {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setCode(code);
        jsonMessage.setMessage(message);
        return jsonMessage;
    }
}

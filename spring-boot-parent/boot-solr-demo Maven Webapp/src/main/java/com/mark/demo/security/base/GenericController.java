package com.mark.demo.security.base;

import java.beans.PropertyEditorSupport;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Maps;
import com.mark.demo.security.utils.BeanValidators;


public abstract class GenericController
{
    private static final Logger logger = Logger.getLogger(GenericController.class.getSimpleName());
    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;
    
    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected boolean beanValidator(Model model, Object object, Class<?> ... groups)
    {
        try
        {
            BeanValidators.validateWithException(validator, object, groups);
        }
        catch (ConstraintViolationException ex)
        {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(model, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }
    
    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?> ... groups)
    {
        try
        {
            BeanValidators.validateWithException(validator, object, groups);
        }
        catch (ConstraintViolationException ex)
        {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(redirectAttributes, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }
    
    /**
     * 添加Model消息
     *
     * @param model
     * @param messages
     */
    protected void addMessage(Model model, String ... messages)
    {
        StringBuilder sb = new StringBuilder();
        for (String message : messages)
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        model.addAttribute("message", sb.toString());
    }
    
    /**
     * 添加Flash消息
     *
     * @param redirectAttributes
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String ... messages)
    {
        StringBuilder sb = new StringBuilder();
        for (String message : messages)
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }
    
    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException()
    {
        return "error/400";
    }
    
    /**
     * 授权登录异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public String authenticationException()
    {
        return "error/403";
    }
    
    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
            
            @Override
            public String getAsText()
            {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
    
    /**
     * 取得站点根目录路径，如：E:/WorkSpace/tomcat/webapps/root/
     * @param request HttpServletRequest
     * @return String 站点根目录
     */
    protected String getRootPath(HttpServletRequest request)
    {
        String path = null;
        try
        {
            path = WebUtils.getRealPath(request.getSession()
                    .getServletContext(), "/");
        }
        catch (FileNotFoundException e)
        {
            logger.error(e);
        }
        return path;
    }
    
    /**
     * 拼接空的错误信息
     * @param name     名称
     * @return
     * @author 施建波
     */
    public String getNuLLMsgStr(String name)
    {
        StringBuilder sb = new StringBuilder();
        return sb.append(name).append("不能为空").toString();
    }

    /**
     * 拼接未选择的错误信息
     * @param name     名称
     * @return
     * @author 施建波
     */
    public String getSelMsgStr(String name)
    {
        StringBuilder sb = new StringBuilder();
        return sb.append("请选择").append(name).toString();
    }

    /**
     * 拼接超过最大字数的错误信息
     * @param name      名称
     * @param count     字数
     * @return
     * @author 施建波
     */
    public String getMaxMsgStr(String name, Integer count)
    {
        StringBuilder sb = new StringBuilder();
        return sb.append(name).append("的长度必须小于").append(count).append("个字")
                .toString();
    }

    /**
     * 组装错误信息
     * @param name         错误字段名称
     * @param message      错误信息
     * @return
     * @author 施建波
     */
    public Map<String, String> getErrMsgMap(String name, String message)
    {
        Map<String, String> errMsg = Maps.newHashMap();
        errMsg.put("errName", name);
        errMsg.put("errMsg", message);
        return errMsg;
    }
    
}

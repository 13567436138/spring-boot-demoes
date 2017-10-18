package com.mark.demo.security.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mark.demo.security.entity.JsonMessage;
import com.mark.demo.security.exception.BusinessException;


public class BusinessExceptionResolver extends AbstractBaseExceptionResolver
{
    private static final Logger LOGGER = Logger.getLogger(BusinessExceptionResolver.class);
    
    @Override
    protected boolean isSupportedException(Exception ex)
    {
        return ex instanceof BusinessException;
    }
    
    @Override
    protected ModelAndView doNormalResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex)
    {
        BusinessException businessException = (BusinessException) ex;
        // LOGGER.error(ex.getMessage(), ex);
        LOGGER.error(ExceptionUtils.getStackTrace(ex));
        ModelAndView mav = this.getModelAndView(request);
        mav.addObject("code", businessException.getCode());
        mav.addObject("message", businessException.getMessage());
        return mav;
    }
    
    @Override
    protected ModelAndView doAjaxResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex)
    {
        BusinessException businessException = (BusinessException) ex;
        HttpMessageConverter<Object> messageConverter = getJsonMessageConverter();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        outputMessage.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        try
        {
            JsonMessage jsonMessage = new JsonMessage();
            jsonMessage.setCode(businessException.getCode());
            jsonMessage.setMessage(businessException.getMessage());
            messageConverter.write(jsonMessage, MediaType.APPLICATION_JSON, outputMessage);
            return new ModelAndView();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    private ModelAndView getModelAndView(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        if (uri.startsWith("/client/agentMobile") || uri.startsWith("/agentMobile")) return new ModelAndView("agent/mobile/agentMobile_error");
        return new ModelAndView("error/500");
    }
}

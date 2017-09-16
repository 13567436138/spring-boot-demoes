package com.mark.demo.shiro.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mark.demo.shiro.constant.ClientConst;
import com.mark.demo.shiro.entity.ClientKey;
import com.mark.demo.shiro.entity.ClientParameter;
import com.mark.demo.shiro.exception.BusinessException;
import com.mark.demo.shiro.service.ClientKeyService;
import com.mark.demo.shiro.utils.IPUtil;
import com.mark.demo.shiro.utils.ParameterUtils;

public class ClientApiInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger LOGGER = Logger.getLogger(ClientApiInterceptor.class);
    
    @Autowired
    private ClientKeyService    clientKeyService;
    
    /**
     * 数据验证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!request.getMethod().equalsIgnoreCase("POST")) throw new BusinessException(ClientConst.ILLEGAL_REQUEST.getCode(),ClientConst.ILLEGAL_REQUEST.getMessage());
        // 如果启用INFO日志, 记录接口调用信息
        if (LOGGER.isInfoEnabled())
        {
            StringBuilder sb = new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort())
                    .append(request.getContextPath()).append(request.getRequestURI());
            String queryString = request.getQueryString();
            if (StringUtils.isNotBlank(queryString)) sb.append("?").append(queryString);
            LOGGER.info("Request url:" + sb.toString());
            Enumeration<?> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements())
            {
                String paramName = (String) parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                StringBuilder valuesString = new StringBuilder(paramName).append(": ");
                for (String paramValue : paramValues)
                    valuesString.append(paramValue);
                LOGGER.info(valuesString.toString());
            }
        }
        // 用户KEY验证
        String userKey = StringUtils.trimToEmpty(request.getParameter(ClientParameter.USER_KEY));
        if (StringUtils.isBlank(userKey)) throw new BusinessException(ClientConst.KEYERROR.getCode(),ClientConst.KEYERROR.getMessage());
        ClientKey clientKey = clientKeyService.findByKey(userKey);
        if (clientKey == null) throw new BusinessException(ClientConst.KEYERROR.getCode(),ClientConst.KEYERROR.getMessage());
        if (clientKey.getUserLimit())
        {
            // 如果需要白名单验证
            Boolean bol = clientKeyService.isExistNetAddress(clientKey.getUserKey(), clientKey.getAccessType(), IPUtil.getIpAddr(request));
            if (!bol) throw new BusinessException(ClientConst.KEYERROR);
        }
        // 异或校验验证
        String userDes = StringUtils.trimToEmpty(request.getParameter(ClientParameter.USER_DES));
        if (StringUtils.isBlank(userDes)) throw new BusinessException(ClientConst.DESERROR);
        // 数据长度验证
        String lenStr = StringUtils.trimToEmpty(request.getParameter(ClientParameter.DATA_LEN));
        if (!StringUtils.isNumeric(lenStr)) throw new BusinessException(ClientConst.LENERROR);
        int dataLen = Integer.parseInt(lenStr);
        String data = request.getParameter(ClientParameter.DATA);
        if (!ParameterUtils.validateClientDataLength(dataLen, data)) throw new BusinessException(ClientConst.LENERROR);
        if (!ParameterUtils.validateClientData(userDes, userKey, dataLen)) throw new BusinessException(ClientConst.DESERROR);
        return true;
    }
}

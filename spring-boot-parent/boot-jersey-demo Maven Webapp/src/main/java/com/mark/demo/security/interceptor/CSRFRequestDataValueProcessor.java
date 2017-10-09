package com.mark.demo.security.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;


public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor
{
    private CSRFTokenManager csrfTokenManager;
    
    public void setCsrfTokenManager(CSRFTokenManager csrfTokenManager)
    {
        this.csrfTokenManager = csrfTokenManager;
    }
    

	@Override
	public String processAction(HttpServletRequest request, String action, String httpMethod) {
		return action;
	}
    
    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value, String type)
    {
        return value;
    }
    
    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request)
    {
        Map<String, String> hiddenFields = new HashMap<>();
        hiddenFields.put(CSRFTokenManager.CSRF_TOKEN_NAME, csrfTokenManager.getTokenForRequest(request));
        return hiddenFields;
    }
    
    @Override
    public String processUrl(HttpServletRequest request, String url)
    {
        return url;
    }

}

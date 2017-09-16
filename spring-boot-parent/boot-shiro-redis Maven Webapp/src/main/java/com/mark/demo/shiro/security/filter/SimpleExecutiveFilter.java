package com.mark.demo.shiro.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.util.PatternMatchUtils;


public class SimpleExecutiveFilter extends AuthorizationFilter
{
    protected static final String[] blackUrlPathPattern = new String[]{"*.aspx*", "*.asp*", "*.php*", "*.exe*", "*.jsp*", "*.pl*", "*.py*", "*.groovy*", "*.sh*", "*.rb*",
            "*.dll*", "*.bat*", "*.bin*", "*.dat*", "*.bas*", "*.so*", "*.cmd*", "*.com*", "*.cpp*", "*.jar*", "*.class*", "*.lnk*"};
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object obj) throws Exception
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String reqUrl = httpRequest.getRequestURI().toLowerCase().trim();
        for (String pattern : blackUrlPathPattern)
        {
            if (PatternMatchUtils.simpleMatch(pattern, reqUrl)) { return false; }
        }
        return true;
    }
}

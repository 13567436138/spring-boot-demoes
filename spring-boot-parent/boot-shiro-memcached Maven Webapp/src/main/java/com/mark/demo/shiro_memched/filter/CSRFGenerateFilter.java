package com.mark.demo.shiro_memched.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.mark.demo.shiro_memched.interceptor.CSRFTokenManager;
import com.mark.demo.shiro_memched.utils.SpringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月13日
*
*/
@WebFilter(filterName="csrf",urlPatterns="/*" )
public class CSRFGenerateFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		request.setAttribute(CSRFTokenManager.CSRF_TOKEN_NAME,((CSRFTokenManager)SpringUtils.getBean(CSRFTokenManager.class)).getTokenForRequest(req));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

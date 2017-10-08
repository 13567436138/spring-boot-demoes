package com.mark.demo.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public class XFrameOptionsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		((HttpServletResponse)response).setHeader("X-Frame-Options", "SAMEORIGIN");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

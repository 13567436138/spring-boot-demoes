package com.mark.demo.batch.admin;

import javax.servlet.annotation.WebFilter;

import org.springframework.web.filter.ShallowEtagHeaderFilter;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
@WebFilter(
		displayName="myShallow",
		urlPatterns={"/*"}
		)
public class MyShallowEtagHeaderFilter extends ShallowEtagHeaderFilter {

}

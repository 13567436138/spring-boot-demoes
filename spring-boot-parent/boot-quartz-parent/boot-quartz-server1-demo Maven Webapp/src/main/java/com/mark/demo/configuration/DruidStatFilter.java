package com.mark.demo.configuration;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/*
*hxp(hxpwangyi@126.com)
*2017年9月24日
*
*/
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
	initParams={
        @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
 	}
)
public class DruidStatFilter extends WebStatFilter {

}

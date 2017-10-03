package com.mark.demo.security.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/*
*hxp(hxpwangyi@126.com)
*2017年9月24日
*
*/
@WebServlet(urlPatterns="/druid/*",
	initParams={
	         @WebInitParam(name="allow",value="192.168.1.72,127.0.0.1"),
	         @WebInitParam(name="deny",value="192.168.1.73"),
	         @WebInitParam(name="loginUsername",value="admin"),
	         @WebInitParam(name="loginPassword",value="123456"),
	         @WebInitParam(name="resetEnable",value="false")
	
	}
)
public class DruidStatViewServlet extends StatViewServlet {

}

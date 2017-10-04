package com.mark.demo.batch.admin;

import javax.jws.soap.InitParam;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.servlet.DispatcherServlet;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
@WebServlet(
		name="myDispatcher",
		urlPatterns={"/"},
		initParams={@WebInitParam(name="contextConfigLocation",value="classpath*:/org/springframework/batch/admin/web/resources/servlet-config.xml")}
	)
public class MyDispatcherServlet extends DispatcherServlet {

}

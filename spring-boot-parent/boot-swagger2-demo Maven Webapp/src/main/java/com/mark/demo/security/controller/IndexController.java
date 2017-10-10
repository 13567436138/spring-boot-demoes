package com.mark.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@Controller
@RequestMapping("/admins/indexes")
public class IndexController {
	
	@RequestMapping("/index")
	public String index(){
		return "admins/main";
	}
}

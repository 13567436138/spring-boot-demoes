package com.mark.demo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mark.demo.security.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	
}

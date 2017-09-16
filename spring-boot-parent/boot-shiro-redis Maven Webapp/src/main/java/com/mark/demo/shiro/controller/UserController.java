package com.mark.demo.shiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mark.demo.shiro.entity.Menu;
import com.mark.demo.shiro.service.UserService;

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
	
	@RequestMapping("/getMenuTop")
	@ResponseBody
	public List<Menu> getMenuTop(){
		return userService.getMenuTopLever();
	}
}

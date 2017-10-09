package com.mark.demo.security.controller;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.mark.demo.security.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
@Path("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Path("/test")
	@Produces(value={MediaType.APPLICATION_JSON})
	public String test(){
		return "test";
	}
}

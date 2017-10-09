package com.mark.demo.security.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@Path("/admins/indexes")
public class IndexController {
	
	@GET  
    @Path("/index")  
    @Produces(MediaType.TEXT_HTML) 
	public String index(){
		return "admins/main";
	}
}

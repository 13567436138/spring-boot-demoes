package com.mark.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
*hxp(hxpwangyi@126.com)
*2017年10月12日
*
*/
@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav=new ModelAndView("index");
		
		return mav;
	}
}

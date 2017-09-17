package com.mark.demo.shiro_memched.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
*hxp(hxpwangyi@126.com)
*2017年9月12日
*
*/
@Controller
@RequestMapping(value = "/test")
public class TestController {
    @RequestMapping(value = "/velocity", method = RequestMethod.GET)
    public String getTest(Model model) {
        model.addAttribute("hello", "test velocity");
        return "test";
    }
}

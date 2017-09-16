package com.mark.demo.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@Controller
public class HelloController {
	
	@RequestMapping("/helloWorld")  
    public String helloWorld(Model model) {  
        String word0 = "Hello ";  
        String word1 = "World!";  
        //将数据添加到视图数据容器中  
        model.addAttribute("word0",word0);  
        model.addAttribute("word1",word1);  
        return "helloworld.ftl";  
    }  
}

package com.mark.demo.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mark.demo.security.base.PaginateResult;
import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/getMenuTopLever")
	@ResponseBody
	public List<Menu> getMenuTopLever(){
		return  menuService.getMenuTopLever();
	}
	
	@RequestMapping("/getMenuChildren")
	@ResponseBody
	public List<Menu> getMenuChildren(int pid){
		return  menuService.getMenuChildren(pid);
	}
	
	@RequestMapping("/updateMenu")
	@ResponseBody
	public boolean updateMenu(Menu menu){
		return menuService.updateMenu(menu);
	}
	
	@RequestMapping("/list")
	public String list(){
		return "admins/system/menu.ftl";
	}
	
	@RequestMapping("/list/data")
	@ResponseBody
	public PaginateResult<Menu> listData(Menu menu,Pagination pagination,HttpServletRequest request){
		return menuService.findPage(pagination, menu);
	}
}

package com.mark.demo.shiro.service;

import java.util.List;

import com.mark.demo.shiro.base.GenericService;
import com.mark.demo.shiro.entity.Menu;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
public interface MenuService extends GenericService<Menu>{
	List<Menu> getMenuTopLever();
	List<Menu> getMenuChildren(int pid);
	boolean updateMenu(Menu menu);
}	

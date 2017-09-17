package com.mark.demo.shiro_memched.service;

import java.util.List;

import com.mark.demo.shiro_memched.base.GenericService;
import com.mark.demo.shiro_memched.entity.Menu;

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

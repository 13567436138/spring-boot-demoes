package com.mark.demo.shiro.mapper;

import java.util.List;

import com.mark.demo.shiro.anno.MyBatisDao;
import com.mark.demo.shiro.base.GenericMapper;
import com.mark.demo.shiro.entity.Menu;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@MyBatisDao
public interface MenuMapper extends GenericMapper<Menu>{
	List<Menu> getMenuTopLever();
	List<Menu> getMenuChildren(int pid);
	void updateMenu(Menu menu);
}

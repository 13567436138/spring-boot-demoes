package com.mark.demo.shiro_memched.mapper;

import java.util.List;

import com.mark.demo.shiro_memched.anno.MyBatisDao;
import com.mark.demo.shiro_memched.base.GenericMapper;
import com.mark.demo.shiro_memched.entity.Menu;

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

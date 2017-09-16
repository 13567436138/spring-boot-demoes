package com.mark.demo.shiro.mapper;

import java.util.List;

import com.mark.demo.shiro.anno.MyBatisDao;
import com.mark.demo.shiro.base.GenericMapper;
import com.mark.demo.shiro.entity.Menu;
import com.mark.demo.shiro.entity.User;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
@MyBatisDao
public interface UserMapper extends GenericMapper<User>{
	User getUserByUserName(String name);
	List<Menu> getMenuTopLever();
}

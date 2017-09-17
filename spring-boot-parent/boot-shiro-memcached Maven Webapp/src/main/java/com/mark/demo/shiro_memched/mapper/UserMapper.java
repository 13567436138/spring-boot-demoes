package com.mark.demo.shiro_memched.mapper;

import java.util.List;

import com.mark.demo.shiro_memched.anno.MyBatisDao;
import com.mark.demo.shiro_memched.base.GenericMapper;
import com.mark.demo.shiro_memched.entity.Menu;
import com.mark.demo.shiro_memched.entity.User;

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

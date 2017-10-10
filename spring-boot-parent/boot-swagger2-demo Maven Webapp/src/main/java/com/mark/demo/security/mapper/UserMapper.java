package com.mark.demo.security.mapper;

import java.util.List;

import com.mark.demo.security.anno.MyBatisDao;
import com.mark.demo.security.base.GenericMapper;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.entity.User;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
@MyBatisDao
public interface UserMapper extends GenericMapper<User>{
	User getUserByUserName(String name);
}

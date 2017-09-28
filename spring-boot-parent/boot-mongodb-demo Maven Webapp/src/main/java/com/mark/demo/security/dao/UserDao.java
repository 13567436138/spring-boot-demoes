package com.mark.demo.security.dao;

import com.mark.demo.security.base.GenericDao;
import com.mark.demo.security.entity.User;

/*
*hxp(hxpwangyi@126.com)
*2017年9月27日
*
*/
public interface UserDao extends GenericDao<User> {
	User getUserByUserName(String username);
}

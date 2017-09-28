package com.mark.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.dao.UserDao;
import com.mark.demo.security.entity.User;
import com.mark.demo.security.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	
	@Autowired(required=true)
	public UserServiceImpl(UserDao userDao) {
		super(userDao);
		this.userDao=userDao;
	}

	@Override
	public List<User> findAll() {
		Query query=new Query();
		return userDao.queryList(query);
	}
	

}

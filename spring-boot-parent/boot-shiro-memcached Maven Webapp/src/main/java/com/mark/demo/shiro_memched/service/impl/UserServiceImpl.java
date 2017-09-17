package com.mark.demo.shiro_memched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.shiro_memched.base.GenericServiceImpl;
import com.mark.demo.shiro_memched.entity.Menu;
import com.mark.demo.shiro_memched.entity.User;
import com.mark.demo.shiro_memched.mapper.UserMapper;
import com.mark.demo.shiro_memched.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
	
	private UserMapper userMapper;
	
	@Autowired(required=true)
	public UserServiceImpl(UserMapper userMapper) {
		super(userMapper);
		this.userMapper=userMapper;
	}
	@Override
	public List<Menu> getMenuTopLever() {
		return userMapper.getMenuTopLever();
	}

}

package com.mark.demo.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.shiro.base.GenericServiceImpl;
import com.mark.demo.shiro.entity.Menu;
import com.mark.demo.shiro.entity.User;
import com.mark.demo.shiro.mapper.MenuMapper;
import com.mark.demo.shiro.mapper.UserMapper;
import com.mark.demo.shiro.service.UserService;

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

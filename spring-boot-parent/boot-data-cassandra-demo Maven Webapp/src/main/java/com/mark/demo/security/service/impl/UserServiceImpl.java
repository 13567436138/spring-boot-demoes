package com.mark.demo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.User;
import com.mark.demo.security.repository.UserRepository;
import com.mark.demo.security.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月8日
*
*/
@Service
public class UserServiceImpl extends GenericServiceImpl<User,Integer> implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired(required=true)
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
		this.userRepository=userRepository;
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	

}

package com.mark.demo.security.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mark.demo.security.entity.User;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	User findByUserName(String userName);
}

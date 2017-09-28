package com.mark.demo.security.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mark.demo.security.base.GenericDaoImpl;
import com.mark.demo.security.dao.UserDao;
import com.mark.demo.security.entity.User;

/*
*hxp(hxpwangyi@126.com)
*2017年9月27日
*
*/
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	@Override
	public User getUserByUserName(String username) {
		Query query=new Query();
		Criteria criteria=Criteria.where("userName").is(username);
		query.addCriteria(criteria);
		return this.queryOne(query);
	}

	

}

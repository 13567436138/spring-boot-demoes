package com.mark.demo.security.base;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

/*
*hxp(hxpwangyi@126.com)
*2017年9月9日
*
*/
public interface GenericService <T extends GenericEntity>{
	List<T> findList(Query query) ;
	void delete(String refrencdId) ;
	void insert(T entity) ;
	void deleteByPrimaryKey(String refrenceid);
	PaginateResult<T> findPage(Pagination page, Query query);
	List<T> findAll();
	
}

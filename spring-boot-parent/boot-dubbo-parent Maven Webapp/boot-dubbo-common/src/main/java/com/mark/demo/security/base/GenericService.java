package com.mark.demo.security.base;

import java.util.List;

/*
*hxp(hxpwangyi@126.com)
*2017年9月9日
*
*/
public interface GenericService <T extends GenericEntity>{
	List<T> findList(T entity) ;
	int delete(String refrencdId) ;
	int insert(T entity) ;
	int deleteByPrimaryKey(String refrenceid);
	PaginateResult<T> findPage(Pagination page, T entity);
	List<T> findAll();
	
}

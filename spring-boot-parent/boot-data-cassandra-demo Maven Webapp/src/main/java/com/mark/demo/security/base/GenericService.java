package com.mark.demo.security.base;

import java.io.Serializable;
import java.util.List;

/*
*hxp(hxpwangyi@126.com)
*2017年9月9日
*
*/
public interface GenericService <T extends GenericEntity,I extends Serializable>{
	void delete(I refrencdId) ;
	T insert(T entity) ;
	void deleteByPrimaryKey(I refrenceid);
	Iterable<T> findAll();
	
}

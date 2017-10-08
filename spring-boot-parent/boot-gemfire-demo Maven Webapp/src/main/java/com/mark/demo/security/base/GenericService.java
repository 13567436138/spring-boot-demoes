package com.mark.demo.security.base;

import java.io.Serializable;

import org.apache.geode.DataSerializable;

/*
*hxp(hxpwangyi@126.com)
*2017年9月9日
*
*/
public interface GenericService <T extends DataSerializable,I extends Serializable>{
	void delete(I refrencdId) ;
	T insert(T entity) ;
	void deleteByPrimaryKey(I refrenceid);
	Iterable<T> findAll();
	
}

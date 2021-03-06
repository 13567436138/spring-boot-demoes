package com.mark.demo.security.repository;


import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mark.demo.security.entity.Resource;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface ResourceRepository extends CassandraRepository<Resource> {

}

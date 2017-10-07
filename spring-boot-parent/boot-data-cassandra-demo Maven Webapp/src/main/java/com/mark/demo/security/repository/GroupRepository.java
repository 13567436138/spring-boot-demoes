package com.mark.demo.security.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.mark.demo.security.entity.Group;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface GroupRepository extends CassandraRepository<Group> {

}

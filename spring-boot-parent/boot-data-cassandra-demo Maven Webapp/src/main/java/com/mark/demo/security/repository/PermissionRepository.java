package com.mark.demo.security.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.mark.demo.security.entity.Permission;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface PermissionRepository extends CassandraRepository<Permission> {

}

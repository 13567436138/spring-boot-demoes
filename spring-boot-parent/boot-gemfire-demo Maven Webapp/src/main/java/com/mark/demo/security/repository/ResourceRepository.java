package com.mark.demo.security.repository;

import org.springframework.data.gemfire.repository.GemfireRepository;

import com.mark.demo.security.entity.Resource;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface ResourceRepository extends GemfireRepository<Resource, Integer> {

}

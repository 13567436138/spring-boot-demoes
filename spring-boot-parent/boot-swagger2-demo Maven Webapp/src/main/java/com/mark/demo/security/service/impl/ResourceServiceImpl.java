package com.mark.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.Resource;
import com.mark.demo.security.mapper.ResourceMapper;
import com.mark.demo.security.service.ResourceService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Service
public class ResourceServiceImpl extends GenericServiceImpl<Resource> implements ResourceService {
	@Autowired
	private ResourceMapper mapper;
	
	@Autowired(required=true)
	public ResourceServiceImpl(ResourceMapper dao){
		super(dao);
		this.mapper=dao;
	}

	
	
}

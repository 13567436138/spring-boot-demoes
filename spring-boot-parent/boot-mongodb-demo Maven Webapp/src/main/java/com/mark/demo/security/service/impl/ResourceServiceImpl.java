package com.mark.demo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.dao.ResourceDao;
import com.mark.demo.security.entity.Resource;
import com.mark.demo.security.service.ResourceService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Service
public class ResourceServiceImpl extends GenericServiceImpl<Resource> implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired(required=true)
	public ResourceServiceImpl(ResourceDao dao){
		super(dao);
		this.resourceDao=dao;
	}

	
	
}

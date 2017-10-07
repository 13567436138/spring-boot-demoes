package com.mark.demo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.Resource;
import com.mark.demo.security.repository.ResourceRepository;
import com.mark.demo.security.service.ResourceService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Service
public class ResourceServiceImpl extends GenericServiceImpl<Resource,Integer> implements ResourceService {
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired(required=true)
	public ResourceServiceImpl(ResourceRepository resourceRepository){
		super(resourceRepository);
		this.resourceRepository=resourceRepository;
	}

	
	
}

package com.mark.demo.shiro_memched.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mark.demo.shiro_memched.base.GenericServiceImpl;
import com.mark.demo.shiro_memched.entity.FastDFSFile;
import com.mark.demo.shiro_memched.mapper.ImageMapper;
import com.mark.demo.shiro_memched.service.ImageService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月14日
*
*/
@Component
public class ImageServiceImpl extends GenericServiceImpl<FastDFSFile> implements ImageService {
	private ImageMapper imageMapper;
	
	@Autowired(required=true)
	public ImageServiceImpl(ImageMapper dao) {
		super(dao);
		this.imageMapper=dao;
	}

	
	
}

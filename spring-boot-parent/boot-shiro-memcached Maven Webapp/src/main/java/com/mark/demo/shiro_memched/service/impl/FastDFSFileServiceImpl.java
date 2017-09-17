package com.mark.demo.shiro_memched.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.shiro_memched.base.GenericServiceImpl;
import com.mark.demo.shiro_memched.entity.FastDFSFile;
import com.mark.demo.shiro_memched.mapper.FastDFSFileMapper;
import com.mark.demo.shiro_memched.service.FastDFSFileService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月12日
*
*/
@Service
public class FastDFSFileServiceImpl extends GenericServiceImpl<FastDFSFile>implements FastDFSFileService {
	private FastDFSFileMapper fastDFSFileMapper;
	
	@Autowired(required=true)
	public FastDFSFileServiceImpl(FastDFSFileMapper dao) {
		super(dao);
		fastDFSFileMapper=dao;
	}
	
}

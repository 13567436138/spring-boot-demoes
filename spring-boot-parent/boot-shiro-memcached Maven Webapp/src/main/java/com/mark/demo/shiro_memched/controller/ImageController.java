package com.mark.demo.shiro_memched.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mark.demo.shiro_memched.base.PaginateResult;
import com.mark.demo.shiro_memched.base.Pagination;
import com.mark.demo.shiro_memched.entity.FastDFSFile;
import com.mark.demo.shiro_memched.entity.User;
import com.mark.demo.shiro_memched.service.ImageService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月13日
*
*/
@Controller
@RequestMapping("/image")
public class ImageController {
	@Autowired
	private ImageService imageService;
	
	@RequestMapping("/list")
	public String list(){
		return "admins/image/list";
	}
	
	
	@RequestMapping("/list/data")
	@ResponseBody
	public PaginateResult<FastDFSFile> listData(FastDFSFile fastDFSFile,Pagination pagination,HttpServletRequest request){
		fastDFSFile.setAuthor(((User)SecurityUtils.getSubject().getPrincipal()).getUserName());
		return imageService.findPage(pagination, fastDFSFile);
	}
}

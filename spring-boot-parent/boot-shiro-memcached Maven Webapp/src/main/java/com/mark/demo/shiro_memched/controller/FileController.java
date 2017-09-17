package com.mark.demo.shiro_memched.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mark.demo.shiro_memched.constant.CommonConst;
import com.mark.demo.shiro_memched.entity.EnumDescribable;
import com.mark.demo.shiro_memched.entity.FastDFSFile;
import com.mark.demo.shiro_memched.entity.User;
import com.mark.demo.shiro_memched.service.FastDFSClientWrapper;
import com.mark.demo.shiro_memched.service.FastDFSFileService;
import com.mark.demo.shiro_memched.utils.StringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月12日
*
*/
@Controller
@RequestMapping("/admins/file")
public class FileController {
	
	@Autowired
	private FastDFSClientWrapper dfsClient;
	@Autowired
	private FastDFSFileService fastDFSFileService;

	@RequestMapping("/upload")
	@ResponseBody
	public EnumDescribable uploadFile(MultipartFile file) throws IOException{
		String path=dfsClient.uploadFile(file);
		Subject subject=SecurityUtils.getSubject();
		String ext=FilenameUtils.getExtension(file.getOriginalFilename());
		FastDFSFile entity=new FastDFSFile(path, file.getOriginalFilename(),ext , file.getSize(), ((User)subject.getPrincipal()).getUserName(),StringUtils.isImage(ext));
		fastDFSFileService.insert(entity);
		return CommonConst.SUCCESS;
	}
	
	@RequestMapping("/show")
	public void showFile(String path,HttpServletResponse response)throws Exception{
		byte[] data=dfsClient.downloadFile(path);
		ServletOutputStream sos=null;
		try{
			response.setContentType("image/*"); 
			sos=response.getOutputStream();
			sos.write(data);
		}finally{
			sos.close();
		}
		
	}
}

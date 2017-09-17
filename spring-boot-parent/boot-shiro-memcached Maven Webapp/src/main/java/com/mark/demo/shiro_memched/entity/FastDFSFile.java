package com.mark.demo.shiro_memched.entity;

import com.mark.demo.shiro_memched.base.GenericEntity;
import com.mark.demo.shiro_memched.constant.FileManagerConfig;

/*
*hxp(hxpwangyi@126.com)
*2017年9月12日
*
*/
public class FastDFSFile extends GenericEntity{
	private String path;
    private String name;
    private String ext;
    private long length;
    private String author = FileManagerConfig.FILE_DEFAULT_AUTHOR;
    private int type;//1文件，2图片
    
    public FastDFSFile(){}
    
    public FastDFSFile(String path, String ext) {
        this.path = path;
        this.ext = ext;
    }

    public FastDFSFile(String path, String name, String ext) {
    	this.path = path;
        this.name = name;
        this.ext = ext;
    }

    public FastDFSFile(String path, String name, String ext, long length,
            String author,int type) {
    	this.path = path;
        this.name = name;
        this.ext = ext;
        this.length = length;
        this.author = author;
        this.type=type;
    }
    
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
}

package com.mark.demo.security.entity;

import com.mark.demo.security.base.GenericEntity;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
public class Resource extends GenericEntity {
	private String role;
	private String url;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

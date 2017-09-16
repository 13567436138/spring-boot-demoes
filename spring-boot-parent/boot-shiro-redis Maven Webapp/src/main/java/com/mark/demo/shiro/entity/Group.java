package com.mark.demo.shiro.entity;

import java.util.List;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
public class Group {
	private int groupId;
	private String groupName;
	private String groupDesc;
	
	private List<Role> roleList;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}

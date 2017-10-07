package com.mark.demo.security.entity;

import org.springframework.data.cassandra.mapping.Table;

import com.mark.demo.security.base.GenericEntity;
@Table
public class Role extends GenericEntity{

    private String roleName;

    private String roleDesc;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}


   
}
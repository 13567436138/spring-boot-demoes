package com.mark.demo.security.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.mark.demo.security.base.GenericEntity;

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

	@Override
	public void fromData(DataInput input) throws IOException, ClassNotFoundException {
		id=input.readInt();
		roleDesc=input.readUTF();
		roleName=input.readUTF();
	}

	@Override
	public void toData(DataOutput output) throws IOException {
		output.writeInt(id);
		output.writeUTF(roleDesc);
		output.writeUTF(roleName);
	}


   
}
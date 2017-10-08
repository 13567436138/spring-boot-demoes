package com.mark.demo.security.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import org.apache.geode.DataSerializable;
import org.apache.geode.Instantiator;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.gemfire.mapping.annotation.Indexed;
import org.springframework.data.gemfire.mapping.annotation.Region;

import com.mark.demo.security.base.GenericEntity;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
@Region("user")
public class User extends GenericEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -540158452160163175L;
	@Indexed
	private String userName;
	private String password;
	private String phone;
	private int age;
	private int sex;
	@Transient
	private List<Role> roleList;
	@Transient
	private List<Group> groupList;
	@Transient
	private boolean rememberMe;

	public User(){}
	
	@PersistenceConstructor
	public User(int id,String userName, String password, String phone, int age, int sex) {
		this.id=id;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
		this.age = age;
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	static {
	    Instantiator.register(new Instantiator(User.class, 1025) {
	        public DataSerializable newInstance() {
	          return new User();
	        }
	      });
	  }

	@Override
	public void fromData(DataInput input) throws IOException, ClassNotFoundException {
		id=input.readInt();
		userName=input.readUTF();
		password=input.readUTF();
		phone=input.readUTF();
		age=input.readInt();
		sex=input.readInt();
	}

	@Override
	public void toData(DataOutput output) throws IOException {
		output.writeInt(id);
		output.writeUTF(userName);
		output.writeUTF(password);
		output.writeUTF(phone);
		output.writeInt(age);
		output.writeInt(sex);
	}

}

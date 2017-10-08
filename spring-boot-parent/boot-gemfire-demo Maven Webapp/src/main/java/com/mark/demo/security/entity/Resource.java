package com.mark.demo.security.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.geode.DataSerializable;
import org.apache.geode.Instantiator;
import org.springframework.data.gemfire.mapping.annotation.Region;

import com.mark.demo.security.base.GenericEntity;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
@Region("resource")
public class Resource extends GenericEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -540158452160163175L;
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
	
	static {
	    Instantiator.register(new Instantiator(Resource.class, 1027) {
	        public DataSerializable newInstance() {
	          return new Resource();
	        }
	      });
	  }
	@Override
	public void fromData(DataInput input) throws IOException, ClassNotFoundException {
		id=input.readInt();
		role=input.readUTF();
		url=input.readUTF();
		
	}
	@Override
	public void toData(DataOutput output) throws IOException {
		output.writeInt(id);	
		output.writeUTF(role);
		output.writeUTF(url);
	}
	
	
}

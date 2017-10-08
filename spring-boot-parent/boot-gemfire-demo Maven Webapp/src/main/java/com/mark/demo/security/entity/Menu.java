package com.mark.demo.security.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.geode.DataSerializable;
import org.apache.geode.Instantiator;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import com.mark.demo.security.base.GenericEntity;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
@Region("menu")
public class Menu extends GenericEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -540158452160163175L;
	private int pid;
	private String menuName;
	private String menuDesc;
	private String link;
	private String icon;
	private int order;
	
	public Menu(){}
	@PersistenceConstructor
	public Menu(int id,int pid, String menuName, String menuDesc, String link, String icon, int order) {
		this.id=id;
		this.pid = pid;
		this.menuName = menuName;
		this.menuDesc = menuDesc;
		this.link = link;
		this.icon = icon;
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	static {
	    Instantiator.register(new Instantiator(Menu.class, 1029) {
	        public DataSerializable newInstance() {
	          return new Menu();
	        }
	      });
	  }
	@Override
	public void fromData(DataInput input) throws IOException, ClassNotFoundException {
		id=input.readInt();
		pid=input.readInt();
		menuName=input.readUTF();
		menuDesc=input.readUTF();
		icon=input.readUTF();
		link=input.readUTF();
		order=input.readInt();
	}
	@Override
	public void toData(DataOutput output) throws IOException {
		output.writeInt(id);
		output.writeInt(pid);
		output.writeUTF(menuName);
		output.writeUTF(menuDesc);
		output.writeUTF(icon);
		output.writeUTF(link);
		output.writeInt(order);
	}

	

}

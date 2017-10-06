package com.mark.demo.security.entity;

import com.mark.demo.security.base.GenericEntity;

/*
*hxp(hxpwangyi@126.com)
*2017年9月5日
*
*/
public class Menu extends GenericEntity{
	private Menu parent;
	private String menuName;
	private String menuDesc;
	private String link;
	private String icon;
	private int order;
	

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

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

}

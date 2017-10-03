package com.mark.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.mapper.MenuMapper;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)	
*2017年9月7日
*
*/
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu> implements MenuService {

	private MenuMapper menuMapper;
	
	@Autowired(required=true)
	public MenuServiceImpl(MenuMapper menuMapper) {
		super(menuMapper);
		this.menuMapper=menuMapper;
	}

	@Override
	public List<Menu> getMenuTopLever() {
		return menuMapper.getMenuTopLever();
	}

	@Override
	public List<Menu> getMenuChildren(int pid) {
		return menuMapper.getMenuChildren(pid);
	}

	@Override
	public boolean updateMenu(Menu menu) {
		menuMapper.updateMenu(menu);
		return true;
	}


}

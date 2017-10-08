package com.mark.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.base.PaginateResult;
import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.repository.MenuRepository;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)	
*2017年9月7日
*
*/
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu,Integer> implements MenuService {

	private MenuRepository menuRepository;
	
	@Autowired(required=true)
	public MenuServiceImpl(MenuRepository menuRepository) {
		super(menuRepository);
		this.menuRepository=menuRepository;
	}

	@Override
	public List<Menu> getMenuTopLever() {
		return menuRepository.getMenuTopLever();
	}

	@Override
	public List<Menu> getMenuChildren(int pid) {
		return menuRepository.getMenuChildren(pid);
	}

	@Override
	public boolean updateMenu(Menu menu) {
		menuRepository.updateMenu(menu);
		return true;
	}

	@Override
	public PaginateResult<Menu> findPage(Pagination pagination, Menu menu) {
		
		return null;
	}


}

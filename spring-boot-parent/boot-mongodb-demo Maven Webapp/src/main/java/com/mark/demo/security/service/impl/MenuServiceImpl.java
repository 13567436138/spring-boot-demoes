package com.mark.demo.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mark.demo.security.base.GenericServiceImpl;
import com.mark.demo.security.dao.MenuDao;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)	
*2017年9月7日
*
*/
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu> implements MenuService {

	private MenuDao menuDao;
	
	@Autowired(required=true)
	public MenuServiceImpl(MenuDao menuDao) {
		super(menuDao);
		this.menuDao=menuDao;
	}

	@Override
	public List<Menu> getMenuTopLever() {
		Query query=new Query();
		Criteria criteria = Criteria.where("parentId").is("-1");  
		query.addCriteria(criteria);
		return  menuDao.queryList(query);
	}

	@Override
	public List<Menu> getMenuChildren(String pid) {
		Query query=new Query();
		Criteria criteria = Criteria.where("parentId").is(pid);  
		query.addCriteria(criteria);
		return  menuDao.queryList(query);
	}

	@Override
	public boolean updateMenu(Menu menu) {
		Map<String,Object>updater=new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(menu.getMenuName())){
			updater.put("menuName", menu.getMenuName());
		}
		if(StringUtils.isNotEmpty(menu.getMenuDesc())){
			updater.put("menuDesc", menu.getMenuDesc());
		}
		if(menu.getOrder()!=0){
			updater.put("order", menu.getOrder());
		}
		int affect=menuDao.update(String.valueOf(menu.getId()), updater);
		return affect==1;
	}


}

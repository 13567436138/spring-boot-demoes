package com.mark.demo.security.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
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
	@Autowired
	private CassandraOperations cassandraOperations;
	
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
		String countQl="select count(*) from menu ";
		String listQl="select * from menu ";
		String where ="where";
		if(StringUtils.isNotEmpty(menu.getMenuName())){
			where += " menuName like '"+menu.getMenuName()+"%'";
		}
		if(StringUtils.isNotEmpty(menu.getMenuDesc())){
			where += " and menuDesc like '"+menu.getMenuDesc()+"%'";
		}
		if(where.length()>5){
			countQl+=where;
			listQl+=where;
		}
		listQl+= " limit "+(pagination.getCurrentPage()-1)*pagination.getPageSize()+" , "+pagination.getPageSize();
		long count=cassandraOperations.query(countQl).one().getLong(0);
		Pageable pageable=new PageRequest(pagination.getCurrentPage(), pagination.getPageSize());
		List<Menu> menuList=cassandraOperations.query(listQl,new RowMapper<Menu>() {

			@Override
			public Menu mapRow(Row row, int rowNum) throws DriverException {
				Menu menu=new Menu();
				menu.setId(row.getInt("id"));
				menu.setLink(row.getString("link"));
				menu.setMenuName(row.getString("menuName"));
				menu.setMenuDesc(row.getString("menuDesc"));
				menu.setIcon(row.getString("icon"));
				menu.setOrder(row.getInt("order"));
				menu.setPid(row.getInt("pid"));
				return menu;
			}
			
		});
		PaginateResult<Menu> result=new PaginateResult<Menu>();
		result.setTotal(count);
		result.setRows(menuList);
		result.setPage(pagination);
		return result;
	}


}

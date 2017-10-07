package com.mark.demo.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Row;
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
		int endIndex=pagination.getCurrentPage()*pagination.getPageSize();
		int startIndex=(pagination.getCurrentPage()-1)*pagination.getPageSize();
		int pageSize=500;
		
		
		long count=cassandraOperations.query(countQl).one().getLong(0);
		
		List<Menu> menuList=new ArrayList<Menu>();
		if(endIndex<=500){
			listQl+= " limit "+pageSize;
			Pageable pageable=new PageRequest(pagination.getCurrentPage(), pagination.getPageSize());
			List<Row> rowList=cassandraOperations.query(listQl).all();
			if(rowList.size()>endIndex){
				rowList=rowList.subList(startIndex, endIndex-1);
			}else{
				rowList=rowList.subList(startIndex, rowList.size()-1);
			}
			for(int i=0;i<rowList.size();i++){
				Row row=rowList.get(i);
				Menu menu2=new Menu();
				menu2.setId(row.getInt("id"));
				menu2.setLink(row.getString("link"));
				menu2.setMenuName(row.getString("menuName"));
				menu2.setMenuDesc(row.getString("menuDesc"));
				menu2.setIcon(row.getString("icon"));
				menu2.setOrder(row.getInt("order"));
				menu2.setPid(row.getInt("pid"));
				menuList.add(menu2);
			}
		}else{
			int i=0;
			List<Row> rowList=null;
			while(i*pageSize<endIndex){
				i++;
				String q="";
				if(i>0){
					Row row=rowList.get(rowList.size()-1);
					q=listQl+"and token(id) > token('"+row.getInt("id")+"')";
				}
				String ql=q+ " limit "+pageSize;
				rowList=cassandraOperations.query(ql).all();
			}
			if(((i-1)*pageSize+rowList.size())<endIndex){
				rowList=rowList.subList(startIndex-(i-1)*pageSize,rowList.size()-1);
			}else{
				rowList=rowList.subList(startIndex-(i-1)*pageSize, endIndex);
			}
			for(int j=0;j<rowList.size();j++){
				Row row=rowList.get(j);
				Menu menu2=new Menu();
				menu2.setId(row.getInt("id"));
				menu2.setLink(row.getString("link"));
				menu2.setMenuName(row.getString("menuName"));
				menu2.setMenuDesc(row.getString("menuDesc"));
				menu2.setIcon(row.getString("icon"));
				menu2.setOrder(row.getInt("order"));
				menu2.setPid(row.getInt("pid"));
				menuList.add(menu2);
			}
		}
		PaginateResult<Menu> result=new PaginateResult<Menu>();
		result.setTotal(count);
		result.setRows(menuList);
		result.setPage(pagination);
		return result;
	}


}

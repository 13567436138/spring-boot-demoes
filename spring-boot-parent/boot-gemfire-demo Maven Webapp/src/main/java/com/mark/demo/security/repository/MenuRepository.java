package com.mark.demo.security.repository;

import java.util.List;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

import com.mark.demo.security.entity.Menu;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface MenuRepository extends GemfireRepository<Menu, Integer> {
	@Query("select * from /menu where pid=-1")
	List<Menu> getMenuTopLever();
	
	@Query("select * from /menu where pid=$1 ")
	List<Menu> getMenuChildren(int pid);
	
	@Query("update /menu set menuName=$1.menuName ,menuDesc=$1.menuDesc,link=$1.link where id=$1.id ")
	void updateMenu(Menu menu);
}

package com.mark.demo.security.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.mark.demo.security.entity.Menu;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public interface MenuRepository extends CassandraRepository<Menu> {
	@Query("select * from menu where pid=-1 allow filtering")
	List<Menu> getMenuTopLever();
	@Query("select * from menu where pid=?0 allow filtering")
	List<Menu> getMenuChildren(int pid);
	@Query("update menu set menuName=?0.menuName,menuDesc=?0.menuDesc,link=?0.link where id=?0.id")
	void updateMenu(Menu menu);
}

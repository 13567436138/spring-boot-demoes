package com.mark.demo.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.mark.demo.security.base.PaginateResult;
import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月7日
*
*/
@Path("/rest/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Path("/getMenuTopLever")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Menu> getMenuTopLever(){
		return  menuService.getMenuTopLever();
	}
	
	@Path("/getMenuChildren")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Menu> getMenuChildren(@QueryParam("pid")int pid){
		return  menuService.getMenuChildren(pid);
	}
	
	@Path("/updateMenu")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateMenu(@Context Menu menu){
		return menuService.updateMenu(menu);
	}
	
	@Path("/list")
	@Produces(MediaType.TEXT_HTML)
	public String list(){
		return "admins/system/menu";
	}
	
	@Path("/list/data")
	@Produces(MediaType.APPLICATION_JSON)
	public PaginateResult<Menu> listData(@QueryParam("menuName")String menuName,@QueryParam("currentPage")Integer currentPage,
			@QueryParam("pageSize")Integer pageSize,@Context HttpServletRequest request){
		Menu menu =new Menu();
		menu.setMenuName(menuName);
		Pagination pagination=new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(pageSize);
		return menuService.findPage(pagination, menu);
	}
}

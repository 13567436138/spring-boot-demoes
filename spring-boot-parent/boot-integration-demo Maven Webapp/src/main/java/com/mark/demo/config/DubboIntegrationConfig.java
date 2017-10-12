package com.mark.demo.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;

import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.service.MenuService;

/*
*hxp(hxpwangyi@126.com)
*2017年10月12日
*
*/
@Configuration
public class DubboIntegrationConfig {
	@Autowired
	private MenuService menuService;
	
	@InboundChannelAdapter(channel="menuChannel",poller=@Poller(fixedRate="5000"))
	public List<Menu> findMenu(){
		return menuService.findAll();
	}
	
	@Transformer(inputChannel = "menuChannel", outputChannel = "processedMenuChannel")
	public List<Menu> transform(List<Menu> payload) {
		for(int i=0;i<payload.size();i++){
			payload.get(i).setMenuDesc(payload.get(i).getMenuDesc()+i);
		}
		return payload;
	}
	
	 @ServiceActivator(inputChannel="processedMenuChannel", outputChannel="output")
	 public void handle(List<Menu> payload, @Headers Map<String, Object> headerMap) {
		 for(int i=0;i<payload.size();i++){
			 menuService.updateMenu(payload.get(i));
		 }
	 }
		 
	 
}

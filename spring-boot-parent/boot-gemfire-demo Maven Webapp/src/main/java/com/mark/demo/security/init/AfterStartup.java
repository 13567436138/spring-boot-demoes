package com.mark.demo.security.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Component;

import com.mark.demo.security.entity.Menu;
import com.mark.demo.security.entity.User;
import com.mark.demo.security.repository.MenuRepository;
import com.mark.demo.security.repository.UserRepository;
import com.mark.demo.security.utils.SpringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年10月9日
*
*/
@Component
public class AfterStartup implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		GemfireTemplate userTemplate=(GemfireTemplate)SpringUtils.getBean("userGemfireTemplate");
		UserRepository userRepository=(UserRepository)SpringUtils.getBean("userRepository");
		userRepository.deleteAll();
		userTemplate.put(1, new User(1,"mark","mark","11111111111",22,1));
		MenuRepository menuRepository=(MenuRepository)SpringUtils.getBean("menuRepository");
		menuRepository.deleteAll();
		GemfireTemplate menuTemplate=(GemfireTemplate)SpringUtils.getBean("menuGemfireTemplate");
		menuTemplate.put(1, new Menu(1,-1,"系统管理","系统管理","","",1));
		menuTemplate.put(2, new Menu(2,1,"角色管理","角色管理","/menu/list","",1));
		menuTemplate.put(3, new Menu(3,1,"权限管理","权限管理","/permission/list","",2));
	}

}

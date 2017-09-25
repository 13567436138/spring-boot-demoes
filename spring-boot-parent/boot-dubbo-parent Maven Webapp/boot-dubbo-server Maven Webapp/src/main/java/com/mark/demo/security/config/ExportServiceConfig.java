package com.mark.demo.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.mark.demo.security.service.MenuService;
import com.mark.demo.security.service.ResourceService;
import com.mark.demo.security.service.UserService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月25日
*
*/
@Configuration
public class ExportServiceConfig extends DubboConfig{
	  @Value("${dubbo.version}")
	  private String dubboVersion;
	  @Bean
      public ServiceBean<MenuService> menuServiceExport(MenuService menuService) {
         ServiceBean<MenuService> serviceBean = new ServiceBean<MenuService>();
         serviceBean.setProxy("javassist");
         serviceBean.setVersion(dubboVersion);
         serviceBean.setInterface(MenuService.class.getName());
         serviceBean.setRef(menuService);
         serviceBean.setTimeout(5000);
         serviceBean.setRetries(3);
         return serviceBean;
     }
	  
	  @Bean
      public ServiceBean<ResourceService> resourceServiceExport(ResourceService resourceService) {
         ServiceBean<ResourceService> serviceBean = new ServiceBean<ResourceService>();
         serviceBean.setProxy("javassist");
         serviceBean.setVersion(dubboVersion);
         serviceBean.setInterface(ResourceService.class.getName());
         serviceBean.setRef(resourceService);
         serviceBean.setTimeout(5000);
         serviceBean.setRetries(3);
         return serviceBean;
     }
	  
	  @Bean
      public ServiceBean<UserService> userServiceExport(UserService userService) {
         ServiceBean<UserService> serviceBean = new ServiceBean<UserService>();
         serviceBean.setProxy("javassist");
         serviceBean.setVersion(dubboVersion);
         serviceBean.setInterface(UserService.class.getName());
         serviceBean.setRef(userService);
         serviceBean.setTimeout(5000);
         serviceBean.setRetries(3);
         return serviceBean;
     }
}

package com.mark.demo.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/*
*hxp(hxpwangyi@126.com)
*2017年9月25日
*
*/
@Configuration
public class DubboConfig {
	@Value("${dubbo.registry.address}")
	private String registryAddress;  
	
	@Value("${dubbo.registry.protocol}")
	private String registryProtocol;
	
	@Value("${dubbo.app.name}")
	private String dubboAppName;
	
	@Value("${dubbo.protocol.port}")
	private String dubboProtocolPort;
	
	  @Bean
      public RegistryConfig registry() {
          RegistryConfig registryConfig = new RegistryConfig();
          registryConfig.setAddress(registryAddress);
          registryConfig.setProtocol(registryProtocol);
          return registryConfig;
      }
     
     @Bean
     public ApplicationConfig applicationConfig() {
         ApplicationConfig applicationConfig = new ApplicationConfig();
         applicationConfig.setName(dubboAppName);
        return applicationConfig;
     }
     
     @Bean
     public MonitorConfig monitorConfig() {
         MonitorConfig mc = new MonitorConfig();
         mc.setProtocol("registry");
         return mc;
     }
     
     @Bean
     public ReferenceConfig referenceConfig() {
         ReferenceConfig rc = new ReferenceConfig();
         rc.setMonitor(monitorConfig());
         return rc;
     }
     
     @Bean
     public ProtocolConfig protocol() {
         ProtocolConfig protocolConfig = new ProtocolConfig();
         protocolConfig.setPort(Integer.parseInt(dubboProtocolPort));
         return protocolConfig;
     }
     
     @Bean
     public ProviderConfig provider() {
         ProviderConfig providerConfig = new ProviderConfig();
         providerConfig.setMonitor(monitorConfig());
         return providerConfig;
     }
}

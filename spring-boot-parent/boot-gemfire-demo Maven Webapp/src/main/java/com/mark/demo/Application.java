package com.mark.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@ServletComponentScan
@CacheServerApplication(locators = "localhost[1099]",logLevel = "info", autoStartup = true, maxConnections = 100)
@EnablePdx(serializerBeanName = "compositePdxSerializer")
@EnableEntityDefinedRegions(basePackages = "com.mark.demo.security.entity")
@EnableGemfireRepositories(basePackages={"com.mark.demo.security.repository"})
@EnableGemfireCacheTransactions

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

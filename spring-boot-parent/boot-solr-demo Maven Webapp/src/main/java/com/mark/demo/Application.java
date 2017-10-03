package com.mark.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@ServletComponentScan
@MapperScan("com.mark.demo.security.mapper")
@EnableSolrRepositories(basePackages={"com.mark.demo.security.repsitory"},multicoreSupport=true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

package com.mark.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mark.demo.security.anno.MyBatisDao;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan(value="com.mark.demo.security.mapper",annotationClass=MyBatisDao.class)
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}

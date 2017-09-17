package com.mark.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.github.tobato.fastdfs.FdfsClientConfig;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@Import(FdfsClientConfig.class)
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan()
@ServletComponentScan
@MapperScan("com.mark.demo.shiro_memched.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

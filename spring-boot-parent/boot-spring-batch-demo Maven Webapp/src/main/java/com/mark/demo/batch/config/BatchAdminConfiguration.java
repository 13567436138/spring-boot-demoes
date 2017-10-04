package com.mark.demo.batch.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import com.mark.demo.batch.admin.MyDatasourceInitializer;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
@Configuration
@ImportResource(locations={"classpath*:/org/springframework/batch/admin/web/resources/webapp-config.xml"})
public class BatchAdminConfiguration {
	
	@Bean
	public DataSourceInitializer myDatasourceInitializer(){
		MyDatasourceInitializer init=new MyDatasourceInitializer();
		init.setEnabled(false);
		return init;
	}
}

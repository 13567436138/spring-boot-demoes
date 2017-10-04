package com.mark.demo.batch.admin;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.init.DataSourceInitializer;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
public class MyDatasourceInitializer extends DataSourceInitializer {

	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

}

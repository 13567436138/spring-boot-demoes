package com.mark.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;

/*
*hxp(hxpwangyi@126.com)
*2017年10月7日
*
*/
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Override
	protected String getKeyspaceName() {
		return "test";
	}

	

}

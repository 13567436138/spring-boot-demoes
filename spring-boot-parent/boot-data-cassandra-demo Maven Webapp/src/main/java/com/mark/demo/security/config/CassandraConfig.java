package com.mark.demo.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/*
*hxp(hxpwangyi@126.com)
*2017年10月6日
*
*/
@Configuration
@EnableCassandraRepositories("com.mark.demo.security.repository")
@EntityScan(basePackages="com.mark.demo.security.entity")
public class CassandraConfig{
    @Bean
    public CassandraOperations operations(CassandraSessionFactoryBean session) throws Exception {
        return new CassandraTemplate(session.getObject(), new MappingCassandraConverter(new BasicCassandraMappingContext()));
    }


}

package com.mark.demo.batch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@Configuration 
public class DruidDBConfig {
	 	private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);  
	 	@Autowired
	 	private Environment enviroment;
	      
	    @Value("${spring.datasource.initialSize}")  
	    private int initialSize;  
	      
	    @Value("${spring.datasource.minIdle}")  
	    private int minIdle;  
	      
	    @Value("${spring.datasource.maxActive}")  
	    private int maxActive;  
	      
	    @Value("${spring.datasource.maxWait}")  
	    private int maxWait;  
	      
	    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
	    private int timeBetweenEvictionRunsMillis;  
	      
	    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
	    private int minEvictableIdleTimeMillis;  
	      
	    @Value("${spring.datasource.validationQuery}")  
	    private String validationQuery;  
	      
	    @Value("${spring.datasource.testWhileIdle}")  
	    private boolean testWhileIdle;  
	      
	    @Value("${spring.datasource.testOnBorrow}")  
	    private boolean testOnBorrow;  
	      
	    @Value("${spring.datasource.testOnReturn}")  
	    private boolean testOnReturn;  
	      
	    @Value("${spring.datasource.poolPreparedStatements}")  
	    private boolean poolPreparedStatements;  
	      
	    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
	    private int maxPoolPreparedStatementPerConnectionSize;  
	      
	    @Value("${spring.datasource.filters}")  
	    private String filters;  
	      
	    @Value("{spring.datasource.connectionProperties}")  
	    private String connectionProperties;  
	      
	    @Bean(name="dataSource")     //声明其为Bean实例  
	    @Primary  //在同样的DataSource中，首先使用被标注的DataSource 
	    @Qualifier("dataSource")
	    public DataSource dataSource(){  
	        DruidDataSource datasource = new DruidDataSource();  
	          
	        datasource.setUrl(enviroment.getProperty("spring.datasource.bussiness.url"));  
	        datasource.setUsername(enviroment.getProperty("spring.datasource.bussiness.username"));  
	        datasource.setPassword(enviroment.getProperty("spring.datasource.bussiness.password"));  
	        datasource.setDriverClassName(enviroment.getProperty("spring.datasource.bussiness.driver-class-name"));  
	          
	        //configuration  
	        datasource.setInitialSize(initialSize);  
	        datasource.setMinIdle(minIdle);  
	        datasource.setMaxActive(maxActive);  
	        datasource.setMaxWait(maxWait);  
	        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
	        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
	        datasource.setValidationQuery(validationQuery);  
	        datasource.setTestWhileIdle(testWhileIdle);  
	        datasource.setTestOnBorrow(testOnBorrow);  
	        datasource.setTestOnReturn(testOnReturn);  
	        datasource.setPoolPreparedStatements(poolPreparedStatements);  
	        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
	        try {  
	            datasource.setFilters(filters);  
	        } catch (SQLException e) {  
	            logger.error("druid configuration initialization filter", e);  
	        }  
	        datasource.setConnectionProperties(connectionProperties);  
	          
	        return datasource;  
	    }  
	    
	    
	    @Bean     //声明其为Bean实例  
	    @Qualifier("monitorDataSource")
	    public DataSource monitorDataSource(){  
	        DruidDataSource datasource = new DruidDataSource();  
	          
	        datasource.setUrl(enviroment.getProperty("spring.datasource.monitor.url"));  
	        datasource.setUsername(enviroment.getProperty("spring.datasource.monitor.username"));  
	        datasource.setPassword(enviroment.getProperty("spring.datasource.monitor.password"));  
	        datasource.setDriverClassName(enviroment.getProperty("spring.datasource.monitor.driver-class-name"));  
	          
	        //configuration  
	        datasource.setInitialSize(initialSize);  
	        datasource.setMinIdle(minIdle);  
	        datasource.setMaxActive(maxActive);  
	        datasource.setMaxWait(maxWait);  
	        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
	        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
	        datasource.setValidationQuery(validationQuery);  
	        datasource.setTestWhileIdle(testWhileIdle);  
	        datasource.setTestOnBorrow(testOnBorrow);  
	        datasource.setTestOnReturn(testOnReturn);  
	        datasource.setPoolPreparedStatements(poolPreparedStatements);  
	        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
	        try {  
	            datasource.setFilters(filters);  
	        } catch (SQLException e) {  
	            logger.error("druid configuration initialization filter", e);  
	        }  
	        datasource.setConnectionProperties(connectionProperties);  
	          
	        return datasource;  
	    }  
	    
	    @Bean("bussinesstxManager")
	    public PlatformTransactionManager bussinesstxManager() {
	        return new DataSourceTransactionManager(dataSource());
	    }
}

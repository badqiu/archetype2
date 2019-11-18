package com.company.project.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfig {
	
	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
    @Bean(value = "demoprojectDataSource",initMethod = "init")
    @ConfigurationProperties("datasource.demoproject")
    public DataSource hiveDataSource(){
        logger.info("Init demoprojectDataSource");
        return DruidDataSourceBuilder.create().build();
    }
    
}

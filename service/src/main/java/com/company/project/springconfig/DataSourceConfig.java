package com.company.project.springconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataSourceConfig {
	
	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
//    @Bean(name = "demoprojectDataSource",initMethod = "init")
//    //@AliasFor("dataSource")
//    @Primary
//    @ConfigurationProperties("datasource.demoproject")
//    public DataSource demoprojectDataSource(){
//        logger.info("Init demoprojectDataSource");
//        return DruidDataSourceBuilder.create().build();
//    }
    
}

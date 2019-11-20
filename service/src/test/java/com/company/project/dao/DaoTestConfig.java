package com.company.project.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.company.project.config.DataSourceConfig;


@Configuration()
@Import(DataSourceConfig.class)
@ComponentScan(basePackages="com.company.project.dao")
public class DaoTestConfig {
	
	
}

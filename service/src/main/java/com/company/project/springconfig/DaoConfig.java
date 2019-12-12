package com.company.project.springconfig;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.rapid.common.jdbc.dialect.Dialect;
import com.github.rapid.common.jdbc.dialect.MySQLDialect;

@Configuration
@ComponentScan(basePackages="com.company.project.dao*")
public class DaoConfig {

	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource);
		return tm;
	}
	
	public Dialect dialect() {
		return new MySQLDialect();
	}
	
}

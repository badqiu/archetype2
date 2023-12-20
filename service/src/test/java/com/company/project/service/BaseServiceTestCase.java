package com.company.project.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.ServiceTestApplication;


//@SpringBootTest(classes={
//		CacheConfig.class,DataSourceConfig.class,
//		I18nConfig.class,MybatisPlusConfig.class,RedisConfig.class,
//		TomcatConfig.class,ValidatorConfig.class,WebMvcConfig.class,
//		DaoConfig.class,ServiceConfig.class,
//})


//@EnableAutoConfiguration
//@SpringBootApplication
/*
@ImportAutoConfiguration({
	CacheConfig.class,DataSourceConfig.class,
	I18nConfig.class,MybatisPlusConfig.class,RedisConfig.class,
	TomcatConfig.class,ValidatorConfig.class,WebMvcConfig.class
})
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceTestApplication.class})
//@ComponentScan(basePackages="com.company.project.service")
@Transactional
public abstract class BaseServiceTestCase {

}



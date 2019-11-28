package com.company.project.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.springconfig.CacheConfig;
import com.company.project.springconfig.DataSourceConfig;
import com.company.project.springconfig.I18nConfig;
import com.company.project.springconfig.MybatisPlusConfig;
import com.company.project.springconfig.RedisConfig;
import com.company.project.springconfig.TomcatConfig;
import com.company.project.springconfig.ValidatorConfig;
import com.company.project.springconfig.WebMvcConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration()
@SpringBootApplication
@ImportAutoConfiguration({
	CacheConfig.class,DataSourceConfig.class,
	I18nConfig.class,MybatisPlusConfig.class,RedisConfig.class,
	TomcatConfig.class,ValidatorConfig.class,WebMvcConfig.class
})
@ComponentScan(basePackages="com.company.project.dao")
@Transactional
public class BaseDaoTestCase extends com.company.project.common.base.BaseDaoTestCase {

}

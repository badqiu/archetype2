package com.company.project;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.company.project.springconfig.CacheConfig;
import com.company.project.springconfig.DataSourceConfig;
import com.company.project.springconfig.I18nConfig;
import com.company.project.springconfig.MybatisPlusConfig;
import com.company.project.springconfig.RedisConfig;
import com.company.project.springconfig.ValidatorConfig;


@EnableAutoConfiguration
@SpringBootApplication(exclude = {})
@ServletComponentScan
//@ImportAutoConfiguration({
//	CacheConfig.class,DataSourceConfig.class,
//	I18nConfig.class,MybatisPlusConfig.class,RedisConfig.class,
//	TomcatConfig.class,ValidatorConfig.class,WebMvcConfig.class
//})
public class ServiceTestApplication {

	/**
	 * 测试 RUN
	 * 1、http://localhost:8080/user/test1
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServiceTestApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}

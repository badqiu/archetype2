package com.company.project.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@EnableAutoConfiguration()
@SpringBootApplication
@ServletComponentScan
public class AdminApplication {

	/**
	 * 测试 RUN
	 * 1、http://localhost:8080/user/test1
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdminApplication.class);
//		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}

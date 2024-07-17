package com.company.project;

import java.sql.Timestamp;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@ServletComponentScan // @WebServlet、@WebFilter、@WebListener等生效
//@ImportResource(locations = {"classpath:demo_xxxxx.xml"})
public class AdminApplication {

	/**
	 * 测试 RUN
	 * 1、http://localhost:8080/user/test1
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdminApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		ConfigurableApplicationContext context = app.run(args);
		System.out.println("success: applicationName:"+context.getApplicationName() + " startupDate:" + new Timestamp(context.getStartupDate()));app.run(args);
	}

}

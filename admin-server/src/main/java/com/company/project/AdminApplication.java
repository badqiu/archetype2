package com.company.project;

import java.sql.Timestamp;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.company.project.util.EnvironmentUtil;

@SpringBootApplication(
	// scanBasePackageClasses =
	// scanBasePackages =
	// exclude =
)

@ServletComponentScan // @WebServlet、@WebFilter、@WebListener等生效
@EnableAsync // @Async 标注生效
@EnableScheduling //@Scheduled 标注生效
@EnableCaching // spring @CacheConfig @Cacheable 标注生效
//@ImportResource(locations = {"classpath:demo_xxxxx.xml"})
public class AdminApplication {

	/**
	 * 测试 RUN 1、http://localhost:8080/user/test1
	 */
	public static void main(String[] args) {
		StandardEnvironment environment = new StandardEnvironment();
		new EnvironmentUtil().setEnvironment(environment);
		
		
		SpringApplication app = new SpringApplication(AdminApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setEnvironment(environment);
		 app.setAdditionalProfiles("custom"); //附加profile激活，用于子模块添加不同配置， 可以激活application-custom.properties
		
		ConfigurableApplicationContext context = app.run(args);
		System.out.println("start spring boot success: applicationName:" + context.getApplicationName() + " startupDate:"+ new Timestamp(context.getStartupDate()));
	}
	
}

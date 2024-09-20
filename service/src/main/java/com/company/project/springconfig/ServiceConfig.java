package com.company.project.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.project.util.ConvertRegisterHelper;

@Configuration
public class ServiceConfig {

	@Bean
	public ConvertRegisterHelper convertRegisterHelper() {
		return new ConvertRegisterHelper();
	}
	
}

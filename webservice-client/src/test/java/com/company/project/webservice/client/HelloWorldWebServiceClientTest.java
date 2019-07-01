package com.company.project.webservice.client;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.company.project.webservice.HelloWorldWebService;


public class HelloWorldWebServiceClientTest {

	@Test
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		HelloWorldWebService helloWorldWebService = ctx.getBean(HelloWorldWebService.class);
		System.out.println(helloWorldWebService.hello("badqiu"));
		
	}
}

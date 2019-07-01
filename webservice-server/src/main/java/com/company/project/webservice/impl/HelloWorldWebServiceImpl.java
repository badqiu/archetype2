package com.company.project.webservice.impl;

import com.company.project.webservice.HelloWorldWebService;
import com.company.project.webservice.dto.HelloDTO;

public class HelloWorldWebServiceImpl implements HelloWorldWebService{

	private int count = 0;
	@Override
	public String hello(String name) {
		count++;
		System.out.println(count + ". hello,name:" + name);
		return count + ". hello:" + name;
	}
	
	@Override
	public String echo(HelloDTO bean) {
		count++;
		System.out.println(count + ". echo:"+bean);
		return bean.toString();
	}

	@Override
	public String ping() {
		return "PONG";
	}

}

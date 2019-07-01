package com.company.project.webservice;

import com.company.project.webservice.dto.HelloDTO;

public interface HelloWorldWebService {

	/**
	 * 调用示例: http://localhost:6060/rpc/HelloWorldWebService/hello?name=badqiu
	 */
	public String hello(String name);
	
	/**
	 * 调用示例: http://localhost:6060/rpc/HelloWorldWebService/echo?name=badqiu&sex=m&age=22
	 */
	public String echo(HelloDTO bean);
	
	/**
	 * 调用示例: http://localhost:6060/rpc/HelloWorldWebService/ping
	 */
	public String ping();
	
}

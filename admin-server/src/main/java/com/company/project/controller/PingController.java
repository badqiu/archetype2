package com.company.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 检查服务是否正常
 * 
 * @author dqiu
 *
 */
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

	@GetMapping
	public String ping() {
		//可以添加执行，查询数据
		
		System.out.println("ping");
		return "PONG";
	}
	
	@GetMapping
	public String echo(String msg) {
		return "hello:"+msg;
	}
	
	@GetMapping
	@ResponseBody
	public String returnBody() {
		return "body";
	}
	
	@GetMapping
	public String exception() {
		throw new IllegalStateException("test_error_message");
	}
	
	@GetMapping
	public String slow() throws InterruptedException {
		Thread.sleep(1000 * 5);
		return "slow";
	}
	
}

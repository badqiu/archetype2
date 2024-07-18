package com.company.project.common.controller;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.common.security.LoginUser;
import com.company.project.common.security.SpringActionSecurityUtil;
import com.company.project.controller.BaseController;
import com.company.project.util.EnvironmentUtil;
import com.github.rapid.common.util.ThreadUtil;

import io.swagger.annotations.Api;

/**
 * 检查服务是否正常
 * 
 * @author dqiu
 *
 */
@Api("系统测试 API")
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(PingController.class);
	
	@GetMapping
	public String ping() throws InterruptedException {
		//可以添加执行，查询数据
		testThreadStartJoin();
		System.out.println("ping");
		return "PONG";
	}
	
	public static void testThreadStartJoin() throws InterruptedException {
		Thread thread = new Thread(() -> {
    		ThreadUtil.sleep(100);
    	},"testThreadStartJoin");
    	thread.start();
    	thread.join();
	}
	
	@GetMapping
	public String echo(String msg) {
		return "hello:"+msg;
	}
	
	@GetMapping
	public String springActiveProfiles() {
		String[] profiels = EnvironmentUtil.getEnvironment().getActiveProfiles();
		return "spring active environment profiels:"+StringUtils.join(profiels,",");
	}
	
	@GetMapping
	public Properties systemProperties() {
		return System.getProperties();
	}
	
	@GetMapping
	public Map<String,String> systemEnvVars() {
		return System.getenv();
	}
	
	@GetMapping
	public LoginUser loginUser() {
		return SpringActionSecurityUtil.getLoginUser();
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
	public String logException() {
		try {
			exception();
		}catch(Exception e) {
			logger.error("error from logException():"+e,e);
		}
		
		return "error";
	}
	
	@GetMapping
	public String slow() throws InterruptedException {
		Thread.sleep(1000 * 5);
		return "slow";
	}
	
}

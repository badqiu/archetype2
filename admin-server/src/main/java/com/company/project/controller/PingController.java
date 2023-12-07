package com.company.project.controller;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.common.security.LoginUser;
import com.company.project.common.security.SpringActionSecurityUtil;
import com.company.project.util.EnvironmentUtil;

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

	@GetMapping
	public String ping() {
		//可以添加执行，查询数据
		
		System.out.println("ping");
		return "PONG";
	}
	
	@GetMapping
	public String echo(String msg) {
		String[] profiels = EnvironmentUtil.getEnvironment().getActiveProfiles();
		return "hello:"+msg + " profiels:"+StringUtils.join(profiels,",");
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
	public String slow() throws InterruptedException {
		Thread.sleep(1000 * 5);
		return "slow";
	}
	
}

package com.company.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api("登录，登出 API")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@PostMapping
	public boolean login(String username,String password) {
		//可以添加执行，查询数据
		
		System.out.println("login success:"+username);
		return true;
	}
	
	@RequestMapping
	public boolean logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return true;
	}
	
}

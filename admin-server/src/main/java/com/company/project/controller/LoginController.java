package com.company.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.common.security.LoginUser;
import com.company.project.common.security.SpringActionSecurityUtil;

import io.swagger.annotations.Api;

@Api("登录，登出 API")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@PostMapping
	public boolean login(String username,String password) {
		Assert.hasText(username,"用户名不能为空");
		Assert.hasText(password,"密码不能为空");
		
		//可以添加执行，查询数据
		
		if(password.equals("pwd")) {
			LoginUser<Long> loginUser = new LoginUser<Long>();
			loginUser.setUsername(username);
			loginUser.setSuperAdmin(true);
			loginUser.setUserId(1L);
			SpringActionSecurityUtil.setLoginUser(loginUser );
			System.out.println("login success:"+username);
			return true;
		}
		
		return false;
		
	}
	
	@GetMapping
	public LoginUser loginUser() {
		return SpringActionSecurityUtil.getLoginUser();
	}
	
	@GetMapping
	public boolean logout(HttpServletRequest request) {
		SpringActionSecurityUtil.logout(request);
		request.getSession().invalidate();
		return true;
	}
	
}

package com.company.project.controller;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import com.company.project.common.security.LoginUser;
import com.company.project.common.security.SpringActionSecurityUtil;

public class BaseControllerTestCase {
	private static Logger logger = LoggerFactory.getLogger(BaseControllerTestCase.class);
	
	public static void setLoginUser() {
		RequestContextHolder.setRequestAttributes(new DispatcherServletWebRequest(new MockHttpServletRequest()));
		LoginUser loginUser = new LoginUser();
		loginUser.setUsername("testuser");
		loginUser.setSuperAdmin(true);
		SpringActionSecurityUtil.setLoginUser(loginUser);

		logger.info("setLoginUser() loginUser:"+loginUser);
	}
	
	@Before
	public void baseBefore() {
		setLoginUser();
	}
	
}

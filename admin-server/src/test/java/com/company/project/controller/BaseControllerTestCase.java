package com.company.project.controller;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import com.company.project.util.security.LoginUser;
import com.company.project.util.security.SpringActionSecurityUtil;

public class BaseControllerTestCase {
	
	protected static Logger logger = LoggerFactory.getLogger(BaseControllerTestCase.class);
	
	protected MockHttpServletRequest request = new MockHttpServletRequest();
	
	public void setLoginUser() {
		setMockRequest();
		
		LoginUser loginUser = new LoginUser();
		loginUser.setUsername("testuser");
		loginUser.setSuperAdmin(true);
		SpringActionSecurityUtil.setLoginUser(loginUser);

		logger.info("setLoginUser() loginUser:"+loginUser);
	}

	protected void setMockRequest() {
		RequestContextHolder.setRequestAttributes(new DispatcherServletWebRequest(request));
	}
	
	@Before
	public void baseBefore() {
		setLoginUser();
	}
	
}

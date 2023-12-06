package com.company.project.enums;

import org.springframework.core.env.Profiles;

public interface Constant {
	
	//只在测试环境，开发环境,预发显示errorLog
	Profiles SHOW_ERROR_LOG_FOR_HTTP_RESPONSE = Profiles.of("dev","test","pre");
	
	final static String APP_NAME = "demoproject Application";
	
}

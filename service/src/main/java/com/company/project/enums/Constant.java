package com.company.project.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Profiles;

import com.company.project.util.EnvironmentUtil;
import com.github.rapid.common.util.ReflectUtil;

/**
 * 常量配置
 * 
 * 可以配置环境修改的配置，不要配置final
 */
public class Constant {
	private static Logger logger = LoggerFactory.getLogger(Constant.class);
	
	
	//只在测试环境，开发环境,预发显示errorLog
	public static Profiles SHOW_ERROR_LOG_FOR_HTTP_RESPONSE = Profiles.of("dev","test","pre");
	
	public static String APP_NAME = "demoproject Application";
	
	
	
	
	
	
	
	
	
	
	//生产环境配置,此处的配置会覆盖Constant的常量配置
	private static class prod{
		public static String APP_NAME = "prod " + Constant.APP_NAME;
	}
	
	//测试环境配置,此处的配置会覆盖Constant的常量配置
	private static class test{
		public static String APP_NAME = "test " + Constant.APP_NAME;
	}
	
	//开发环境配置,此处的配置会覆盖Constant的常量配置
	private static class dev{
		public static String APP_NAME = "dev " + Constant.APP_NAME;
	}
	
	static {
		overrideConstantValuesByActiveProfile();
	}
	
	private static void overrideConstantValuesByActiveProfile() {
		//激活环境配置，用配置Constant配置
		String msg = "覆盖常量配置: ReflectUtil.modifyAllStaticVariables(Constant.class) profile:"+EnvironmentUtil.getActiveProfile();
		if(EnvironmentUtil.acceptsProdProfiles()) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, prod.class);
		}else if(EnvironmentUtil.acceptsTestProfiles()) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, test.class);
		}else if(EnvironmentUtil.acceptsDevProfiles()) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, dev.class);
		}
	}
	
}

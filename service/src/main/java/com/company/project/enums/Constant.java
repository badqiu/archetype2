package com.company.project.enums;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import com.company.project.util.EnvironmentUtil;
import com.github.rapid.common.util.ReflectUtil;

/**
 * 常量配置
 * 
 * 可以配置环境修改的配置，不要配置final
 */
@Component
public class Constant implements EnvironmentAware,PriorityOrdered{
	private static Logger logger = LoggerFactory.getLogger(Constant.class);
	
	static {
		overrideConstantValuesByActiveProfile();
	}
	
	
	//只在测试环境，开发环境,预发显示errorLog
	public static Profiles SHOW_ERROR_LOG_FOR_HTTP_RESPONSE = Profiles.of("dev","test","pre");
	
	public static String APP_NAME = "demoproject Application";
	
	public static final String LOGGER_MDC_CONTEXT_INFO = "contextInfo";

	public static String NACOS_SERVER_ADDR = "http://www.baidu.com";
	public static String NACOS_NAMESPACE = "public";
	public static String NACOS_GROUP = null;
	
	public static int LOCAL_CACHE_MAXIMUM_SIZE = 10000;
	
	
	public static boolean SWAGGER_API_DOC_ENABLE = true;
	
	//生产环境配置,此处的配置会覆盖Constant的常量配置
	private static class prod{
		public static String APP_NAME = "prod " + Constant.APP_NAME;
		
		public static boolean SWAGGER_API_DOC_ENABLE = false;
		
		public static String NACOS_SERVER_ADDR = "http://www.baidu.com";
		public static String NACOS_NAMESPACE = "public";
		public static String NACOS_GROUP = "DEFAULT_GROUP";
	}
	
	//测试环境配置,此处的配置会覆盖Constant的常量配置
	private static class test{
		public static String APP_NAME = "test " + Constant.APP_NAME;
	}
	
	//开发环境配置,此处的配置会覆盖Constant的常量配置
	private static class dev{
		public static String APP_NAME = "dev " + Constant.APP_NAME;
	}
	

	
	public static void overrideConstantValuesByActiveProfile() {
		//激活环境配置，用配置Constant配置
		String activeProfile = EnvironmentUtil.getActiveProfile();
		overrideConstantValuesByActiveProfile(activeProfile);
	}
	
	public static void overrideConstantValuesByActiveProfile(String activeProfile) {
		String msg = "覆盖常量配置: ReflectUtil.modifyAllStaticVariables(Constant.class) profile:"+activeProfile;
		if("prod".equals(activeProfile)) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, prod.class);
		}else if("test".equals(activeProfile)) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, test.class);
		}else if("dev".equals(activeProfile)) {
			logger.info(msg);
			ReflectUtil.modifyAllStaticVariables(Constant.class, dev.class);
		}else {
			
			String unknowMsg = "未知的profile:"+activeProfile+" 无法修改Constant的常量,请设置环境变量SPRING_PROFILES_ACTIVE";
			
			if(SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC) {
				logger.warn(unknowMsg);
			}else {
				logger.error(unknowMsg);
			}
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		String[] activeProfiles = environment.getActiveProfiles();
		if(ArrayUtils.isNotEmpty(activeProfiles)) {
			overrideConstantValuesByActiveProfile(activeProfiles[0]);
		}
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
}

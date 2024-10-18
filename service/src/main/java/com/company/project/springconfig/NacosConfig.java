package com.company.project.springconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.company.project.enums.Constant;
import com.company.project.util.EnvironmentUtil;
import com.company.project.util.NacosConfigUtil;

import freemarker.core.Environment;

/**
 * 使用nacos配置自动更新常量类 Constant的field值 
 */
@Configuration
@Component
public class NacosConfig implements InitializingBean{

	String nacosServerAddr = Constant.NACOS_SERVER_ADDR;
	String nacosNamespace = Constant.NACOS_NAMESPACE;
	
	@Bean
	public ConfigService configService() {
		try {
			ConfigService result = NacosConfigUtil.createConfigService(nacosServerAddr, nacosNamespace);
			return result;
		} catch (NacosException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String nacosGroup = EnvironmentUtil.getActiveProfile();
		NacosConfigUtil.autoRefreshConfigClass(Constant.class, nacosGroup, configService());
	}
	
	
}

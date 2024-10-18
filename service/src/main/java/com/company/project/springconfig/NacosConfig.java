package com.company.project.springconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.company.project.enums.Constant;
import com.company.project.util.NacosConfigUtil;

@Configuration
@Component
public class NacosConfig implements InitializingBean{

	String nacosNamespace = "public";
	String nacosServerAddr = "http://www.baidu.com";
	String nacosGroup = null;
	
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
		NacosConfigUtil.autoRefreshConfigClass(Constant.class, nacosGroup, configService());
	}
	
	
}

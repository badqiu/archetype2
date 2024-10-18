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

	String nacosServerAddr = Constant.NACOS_SERVER_ADDR;
	String nacosNamespace = Constant.NACOS_NAMESPACE;
	String nacosGroup = Constant.NACOS_GROUP;
	
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

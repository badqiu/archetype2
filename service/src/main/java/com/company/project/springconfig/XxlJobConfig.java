package com.company.project.springconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

@Configuration
public class XxlJobConfig {
    private static Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Value("${xxljob.adminAddress:http://test.demo.com/xxl-job-admin}")
    private String adminAddress;
    
    @Value("${spring.application.name}")
    private String appName;

    @Value("${xxljob.accessToken")
	private String accessToken;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxlJobExecutor() xxl-job config init. appName:"+appName + " adminAddress:"+adminAddress);
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddress);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        return xxlJobSpringExecutor;
    }



}

package com.company.project.web.springconfig;

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
    private String appname;

    @Value("${xxljob.accessToken")
	private String accessToken;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxlJobExecutor() xxl-job config init. appname:"+appname + " adminAddress:"+adminAddress);
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddress);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        return xxlJobSpringExecutor;
    }



}

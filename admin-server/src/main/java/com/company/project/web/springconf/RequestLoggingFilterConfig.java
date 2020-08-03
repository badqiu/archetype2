package com.company.project.web.springconf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * 
 * 用于打印请求日志
 * 需要配置:
 * spring boot application.properties
 * logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
 * 
 * 或者
 * log4j:
 * logger.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
 * 
 * 
 * 
 * @author badqiu
 *
 */
@Configuration
public class RequestLoggingFilterConfig {
 
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
          = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
    
}
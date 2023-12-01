package com.company.project.web.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.company.project.web.filter.TraceIdFilter;
import com.github.rapid.common.web.filter.PerformanceFilter;

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
public class WebFilterConfig {
 
//    @Bean
//    public CommonsRequestLoggingFilter logFilter() {
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(false);
//        filter.setMaxPayloadLength(10000);
//        filter.setIncludeHeaders(false);
//        filter.setAfterMessagePrefix("REQUEST DATA : ");
//        return filter;
//    }
    
	@Bean
    public CharacterEncodingFilter characterEncodingFilter() {
		return new CharacterEncodingFilter("UTF-8",true);
    }
	
	/**
	 * 性能及slowlog filter
	 *  */
	@Bean
    public PerformanceFilter performanceFilter() {
		PerformanceFilter r = new PerformanceFilter();
		return r;
    }
	
	@Bean
	public TraceIdFilter traceIdFilter() {
		return new TraceIdFilter();
	}
	
    
}
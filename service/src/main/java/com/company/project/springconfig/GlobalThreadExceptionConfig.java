package com.company.project.springconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 全局线程异常捕获
 */
@Component
public class GlobalThreadExceptionConfig implements InitializingBean{

	protected static final Logger log = LoggerFactory.getLogger(GlobalThreadExceptionConfig.class);
	
	/**
     * 设置全局异常处理器
     */
    public static void setupGlobalExceptionHandler() {
    	
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            log.error("全局未捕获异常 - 线程: {}", thread.getName(), throwable);
        });
        
        log.info("setupGlobalExceptionHandler() executed");
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		setupGlobalExceptionHandler();
	}
    
}

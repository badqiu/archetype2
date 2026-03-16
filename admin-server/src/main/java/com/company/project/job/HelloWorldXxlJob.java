package com.company.project.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

@Component
public class HelloWorldXxlJob {
	
	private static Logger logger = LoggerFactory.getLogger(HelloWorldXxlJob.class);
	
	@XxlJob("helloworldJobHandler")
	public void helloworldJobHandler() throws Exception {
		String param = XxlJobHelper.getJobParam();    // 获取参数
	    XxlJobHelper.log("XXL-JOB, Hello World.  param:"+param);
	    logger.info("hello world by xxl job:"+param);
	    XxlJobHelper.handleSuccess("helloworldJobHandler success execute");
	}

}

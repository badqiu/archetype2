package com.company.project.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseJob {
	private static Logger logger = LoggerFactory.getLogger(BaseJob.class);
	
	public static boolean disableCronJob = true; //默认关闭定时任务，通过xxljob设置为false然后一台机器运行
	
	public static void sendNotifyMsg(String title,String content) {
		logger.info("sendNotifyMsg() title:{} content:{}",title,content);
	}
	
}

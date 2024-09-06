package com.company.project.cron;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rapid.common.util.NotifyUtil;

public abstract class BaseJob {
	private static Logger logger = LoggerFactory.getLogger(BaseJob.class);
	
	public static boolean disableCronJob = true; //默认关闭定时任务，通过xxljob设置为false然后一台机器运行
	
	public static void sendNotifyMsg(String title,String content) {
		logger.info("sendNotifyMsg() title:{} content:{}",title,content);
		
		//避免太多相同通知
		String notifyIntervalKey = "sendNotifyMsg():" + title;
		if(NotifyUtil.isTooManySameNotify(notifyIntervalKey , Duration.ofMinutes(60))) {
			return;
		}
		
	}
	
}

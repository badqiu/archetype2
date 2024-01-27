package com.company.project;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

	static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	
	@Test
	public void test() throws InterruptedException {
		logger.debug("debug hello");
		logger.info("info hello");
		logger.warn("warn hello");
		logger.error("error hello");
		
		Thread.sleep(1000 * 5);
	}
}

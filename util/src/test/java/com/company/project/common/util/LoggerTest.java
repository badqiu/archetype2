package com.company.project.common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

	static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	@Test
	public void test() {
		logger.debug("debug hello");
		logger.info("info hello");
		logger.warn("warn hello");
		logger.error("error hello");
	}
}

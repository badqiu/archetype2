package com.company.project.enums;

import org.junit.Test;

public class ConstantTest {

	@Test
	public void printAppName() {
		System.out.println("APP_NAME:" + Constant.APP_NAME);
		Constant.overrideConstantValuesByActiveProfile("dev");
		Constant.overrideConstantValuesByActiveProfile("test");
		Constant.overrideConstantValuesByActiveProfile("prod");
		System.out.println("APP_NAME:" + Constant.APP_NAME);
	}
	

}

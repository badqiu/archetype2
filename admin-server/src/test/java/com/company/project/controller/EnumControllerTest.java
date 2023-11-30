package com.company.project.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnumControllerTest {

	EnumController controller = new EnumController();
	
	@Test
	public void test() throws Exception {
		System.out.println(controller.getAllEntity());
		System.out.println(controller.getAllEnum());
		System.out.println(controller.getEntityAllInput());
	}

}

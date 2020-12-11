package com.company.project.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.rapid.common.test.util.BeanAssert;
import com.github.rapid.common.util.ScanClassUtil;

/**
 * 将整个包的 get set方法 进行单元测试
 * 
 * @author badqiu
 *
 */
public class AllModelTest {

	@Test
	public void testAllBean() throws Exception {
		testBeansByPackage("com.company.project.model");
		testBeansByPackage("com.company.project.query");
	}

	public static List<Exception> testBeansByPackage(String javaPackage) throws Exception {
		List<Exception> exceptions = new ArrayList<Exception>();
		List<String> classList = ScanClassUtil.scanPackages(javaPackage);
		for (String clazzString : classList) {
			if (clazzString.endsWith("package-info")) {
				continue;
			}
			if (clazzString.endsWith("Test") || clazzString.startsWith("Test")) {
				continue;
			}
			Class clazz = Class.forName(clazzString);
			System.out.println("test get() set() for " + clazz);
			
			try {
				BeanAssert.testPropertiesAndCommonMethod(clazz);
			}catch(Throwable e) {
				e.printStackTrace();
				exceptions.add(new RuntimeException("test error on class:"+clazz,e));
			}
		}
		return exceptions;
	}
}
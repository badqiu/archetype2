/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */

package com.company.project.service.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.company.project.CityDataFactory;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.company.project.model.*;
import com.company.project.query.*;
import com.company.project.dao.*;

/**
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-28
 */
public class CityServiceImplTest {

	//mock框架使用Mockito 具体使用请查看: http://code.google.com/p/mockito/wiki/MockitoVSEasyMock
	
	private CityServiceImpl service = new CityServiceImpl();
	private CityDao cityDao = mock(CityDao.class);
	
	@Rule public TestName testName = new TestName();
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		service.setCityDao(cityDao);
	}
	
	@Test
	public void test_create() {
		City obj = CityDataFactory.newCity();
		service.create(obj);
		
		verify(cityDao).insert(obj); //验证执行了该语句
	}
	
	@Test
	public void test_update() {
		when(cityDao.getById(new Integer("1"),new Integer("1"))).thenReturn(CityDataFactory.newCity()); // mock方法调用
		
		City obj = CityDataFactory.newCity();
		service.update(obj);
		
		verify(cityDao).update(any()); //验证执行了该语句
	}
	
	@Test
	public void test_removeById() {
		service.removeById(new Integer("1"),new Integer("1"));
		
		verify(cityDao).deleteById(new Integer("1"),new Integer("1")); //验证执行了该语句
	}
	
	@Test
	public void test_getById() {
		when(cityDao.getById(new Integer("1"),new Integer("1"))).thenReturn(CityDataFactory.newCity()); // mock方法调用
		
		City city = service.getById(new Integer("1"),new Integer("1"));
		
		verify(cityDao).getById(new Integer("1"),new Integer("1")); //验证执行了该语句
		assertNotNull(city);
	}
	
	
}


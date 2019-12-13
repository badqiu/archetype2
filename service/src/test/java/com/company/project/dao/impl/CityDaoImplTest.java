/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao.impl;

import com.company.project.CityDataFactory;

import com.github.rapid.common.util.page.Page;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;



import static junit.framework.Assert.*;
import com.company.project.model.*;
import com.company.project.query.*;
import com.company.project.dao.*;


/**
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-12-13
 */
public class CityDaoImplTest extends BaseDaoTestCase {
	
	@Rule public TestName testName = new TestName();
	
	private CityDao dao;
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
	}
	
	@Autowired
	public void setCityDao(CityDao dao) {
		this.dao = dao;
	}

	//数据库单元测试前会开始事务，结束时会回滚事务，所以测试方法可以不用关心测试数据的删除
	@Test
	public void findPage() {

		CityQuery query = CityDataFactory.newCityQuery();
		Page page = dao.findPage(query);
		
		assertEquals(1,page.getPaginator().getPage());
		assertEquals(10,page.getPaginator().getPageSize());
		List resultList = (List)page.getItemList();
		assertNotNull(resultList);
		
	}
	
	@Test
	public void insert() {
		dao.insert(CityDataFactory.newCity());
	}
	
	@Test
	public void update() {
		dao.update(CityDataFactory.newCity());
	}
	
	@Test
	public void delete() {
		dao.deleteById(new City(new Integer("1")));
	}
	
	@Test
	public void getById() {
		dao.getById(new City(new Integer("1")));
	}
	
	
}


/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.project.CityDataFactory;
import com.company.project.dao.CityDao;
import com.company.project.query.CityQuery;
import com.github.rapid.common.util.page.Page;


/**
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-28
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
	public void testFindPage() {

		CityQuery query = CityDataFactory.newCityQuery();
		Page page = dao.findPage(query);
		
		assertEquals(1,page.getPaginator().getPage());
		assertEquals(10,page.getPaginator().getPageSize());
		List resultList = (List)page.getItemList();
		assertNotNull(resultList);
		
	}
	
	@Test
	public void testInsert() {
		dao.insert(CityDataFactory.newCity());
	}
	
	@Test
	public void testUpdate() {
		dao.update(CityDataFactory.newCity());
	}
	
	@Test
	public void testDelete() {
		dao.deleteById(new Integer("1"),new Integer("1"));
	}
	
	@Test
	public void testGetById() {
		dao.getById(new Integer("1"),new Integer("1"));
	}
	
	
}


/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.model.City;
import com.github.rapid.common.jdbc.sqlgenerator.CacheSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.Table;


/**
 * @author 
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {DaoTestConfig.class})
@Transactional
public class SqlGeneratorTest {
	
	@Rule public TestName testName = new TestName();

	private CacheSqlGenerator sqlGenerator = null;
	private Table table;
	@Test
	public void testOther() {
		table = MetadataCreateUtils.createTable(City.class);
		sqlGenerator = new CacheSqlGenerator(new SpringNamedSqlGenerator(table));
		
		System.out.println(sqlGenerator.getUpdateByPkSql());
		System.out.println(sqlGenerator.getDeleteByPkSql());
		System.out.println(sqlGenerator.getInsertSql());
		System.out.println(sqlGenerator.getSelectByPkSql());
	}
	
	
}


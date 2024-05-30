package com.company.project.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import com.company.project.ServiceTestApplication;



@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceTestApplication.class})
@JdbcTest
@Transactional
public abstract class BaseDaoTestCase {

}

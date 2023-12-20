package com.company.project.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.ServiceTestApplication;



@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceTestApplication.class})
@Transactional
public abstract class BaseDaoTestCase {

}

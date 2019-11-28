package com.company.project.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.dao.DaoTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DaoTestConfig.class})
@Transactional
public class BaseDaoTestCase extends com.company.project.common.base.BaseDaoTestCase {

}

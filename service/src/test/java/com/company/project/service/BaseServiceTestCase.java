package com.company.project.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.ServiceTestApplication;




@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceTestApplication.class})
@Transactional
public abstract class BaseServiceTestCase {

}



package com.company.project.common.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.project.ServiceTestApplication;



/**
 * 本基类主要为子类指定好要装载的spring配置文件
 *
 * @author badqiu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceTestApplication.class})
public class BaseDaoTestCase {
}

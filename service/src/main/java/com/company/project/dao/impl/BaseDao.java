package com.company.project.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.rapid.common.jdbc.dao.support.BaseSpringJdbcDao;
import com.github.rapid.common.jdbc.dialect.Dialect;

public abstract class BaseDao extends BaseSpringJdbcDao{

	@Autowired
	public void setDialect(Dialect d) {
		super.setDialect(d);
	}
	
}

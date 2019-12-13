/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao;

import com.company.project.model.*;
import com.company.project.query.*;

import java.util.*;
import com.github.rapid.common.util.page.Page;

/**
 * tableName: city
 * [City] 的Dao操作
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-12-13
*/
public interface CityDao {
	
	public void insert(City entity);
	
	public int update(City entity);

	public int deleteById(City entity);
	
	public City getById(City entity);
	

	public Page<City> findPage(CityQuery query);	
	
	public List<City> findList(CityQuery query);
	
}

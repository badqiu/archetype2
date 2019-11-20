/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao;

import java.util.List;

import com.company.project.model.City;
import com.github.rapid.common.util.page.Page;

/**
 * tableName: city
 * [City] 的Dao操作
 * 
 * @author 
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-18
*/
public interface CityDao {
	
	public void insert(City entity);
	
	public int update(City entity);

	public int deleteById(int id);
	
	public City getById(int id);
	
	
}

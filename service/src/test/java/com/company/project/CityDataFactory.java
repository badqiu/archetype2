/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project;

import java.util.*;

import com.company.project.model.*;
import com.company.project.query.*;

/**
 * 用于生成City相关数据对象的默认值
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-12-13
 * 
 */
public class CityDataFactory {
	
	public static CityQuery newCityQuery() {
		CityQuery query = new CityQuery();
		query.setPage(1);
		query.setPageSize(10);
		
	  	query.setId(new Integer("1"));
	  	query.setProvinceId(new Integer("1"));
	  	query.setCityName(new String("1"));
	  	query.setDescription(new String("1"));
		return query;
	}
	
	public static City newCity() {
		City obj = new City();
		
	  	obj.setId(new Integer("1"));
	  	obj.setProvinceId(new Integer("1"));
	  	obj.setCityName(new String("1"));
	  	obj.setDescription(new String("1"));
		return obj;
	}
}
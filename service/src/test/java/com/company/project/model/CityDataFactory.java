/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.model;


/**
 * 用于生成City相关数据对象的默认值
 * 
 * @author 
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-18
 * 
 */
public class CityDataFactory {
	
	
	public static City newCity() {
		City obj = new City();
		
	  	obj.setId(new Integer("1"));
	  	obj.setProvinceId(new Integer("1"));
	  	obj.setCityName(new String("1"));
	  	obj.setDescription(new String("1"));
		return obj;
	}
}
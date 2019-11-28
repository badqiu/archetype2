/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.webservice.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.company.project.model.*;
import com.company.project.query.*;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;

import java.util.Date;
import java.util.List;

import com.company.project.service.CityService;
import com.company.project.webservice.CityWebService;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;

/**
 * [City] 的WebService接口实现
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0
 */
public class CityWebServiceImpl implements CityWebService {

	@Autowired
	private CityService cityService;

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public void create(City city) {
		cityService.create(city);
	}

	@Override
	public void update(City city) {
		cityService.update(city);
	}

	@Override
	public void removeById(int id, int provinceId) {
		cityService.removeById(id,provinceId);
	}

	@Override
	public City getById(boolean join,int id, int provinceId) {
		City result = cityService.getById(id,provinceId);
		if(join) cityService.join(result);
		return result;
	}

	@Override
	public Page<City> findPage(boolean join,CityQuery query) {
		Assert.isTrue(query.getPageSize() <= 1000,"query.pageSize too large");
		Page<City> result = cityService.findPage(query);
		if(join) result.forEach(cityService::join);
		return result;
	}
	
	@Override
	public List<City> search(boolean join,String query,PageQuery pageQuery){
		throw new RuntimeException("not yet impl");
	}
    
}

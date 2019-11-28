/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.company.project.dao.CityDao;
import com.company.project.model.City;
import com.company.project.query.CityQuery;
import com.company.project.service.CityService;
import com.github.rapid.common.util.holder.BeanValidatorHolder;
import com.github.rapid.common.util.page.Page;

/**
 * [City] 的Service接口实现
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-28
 */
@Service("cityService")
public class CityServiceImpl extends BaseService implements CityService {

	protected static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	
	/*
	* 请删除无用的方法，本代码生成器的理念是: 1. 一次生成，后期手工修改代码 2. 删除代码比手写重复代码快捷，所以请删除无用代码
	*/
	@Autowired
	private CityDao cityDao;
	
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setCityDao(CityDao dao) {
		this.cityDao = dao;
	}
	
    /** 检查到有错误请直接抛异常，不要使用 return errorCode的方式 */
    public void checkCity(City city) {
    	// Bean Validator检查,属性检查失败将抛异常
    	BeanValidatorHolder.validateWithException(city);
    }
    
	/** 
	 * 创建City
	 **/
	@Override
	public City create(City city) {
	    Assert.notNull(city,"'city' must be not null");

	    //init default value
	    
	    checkCity(city);
	    
	    cityDao.insert(city);
	    return city;
	}
	
	/** 
	 * 更新City
	 **/
	@Override
    public City update(City city) {
        Assert.notNull(city,"'city' must be not null");
        checkCity(city);
        
		Integer id = city.getId();
		Integer provinceId = city.getProvinceId();
		
		//不可以让客户端可以更新所有属性
		City fromDb = getById(id,provinceId);
		BeanUtils.copyProperties(city, fromDb,"createTime"); //ignore some copy property
		
		cityDao.update(fromDb);
		
        return fromDb;
    }	
    
    /**
     *  join取回City的关联对象,如一对多，多对一等的关联对象,如: user找到关联的 地址列表 
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public City join(City city) {
    	return city;
    }
    
	/** 
	 * 删除City
	 **/
    @Override
    public void removeById(int id, int provinceId) {
        cityDao.deleteById(id,provinceId);
    }
    
	/** 
	 * 根据ID得到City
	 **/
    @Override
    public City getById(int id, int provinceId) {
        return cityDao.getById(id,provinceId);
    }
    
    /** 
	 * 根据ID得到City,找不到抛异常
	 **/
    @Override
    public City getRequiredById(int id, int provinceId) {
    	City r = getById(id,provinceId);
    	if(r == null) {
    		throw new IllegalArgumentException("required City not found by id:"+Arrays.asList(id,provinceId));
    	}
    	return r;
    }
    
	/** 
	 * 分页查询: City
	 **/      
	@Transactional(readOnly=true)
	public Page<City> findPage(CityQuery query) {
	    Assert.notNull(query,"'query' must be not null");
	    Page<City> r = cityDao.findPage(query);
	    return r;
	}
	
	@Transactional(readOnly=true)
	public List<City> findList(CityQuery query) {
		Assert.notNull(query,"'query' must be not null");
		List<City> r = cityDao.findList(query);
	    return r;
	}
	

	/** 
	 * 行数据权限(实体权限检查),在用户Controller(非管理)或WebService层调用, 请自行实现
	 * @param userId 登录用户ID
	 * @throws SecurityException 没有权限时抛出 
	 **/
	public void checkEntityPermission(long userId,City city,String permission) {
		super.checkEntityPermission(userId,city,permission);
	}
    

}

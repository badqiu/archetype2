/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.service;

import com.github.rapid.common.util.page.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.company.project.model.*;
import com.company.project.dao.*;

import java.util.Date;
import java.util.List;

/**
 * [City] 的Service接口
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-25
 */
public interface CityService {

	/** 
	 * 创建City
	 **/
	public City create(City city);
	
	/** 
	 * 更新City
	 **/	
    public City update(City city);
    
    /**
     *  join取回City的关联对象,如一对多，多对一等的关联对象
     */
    public City join(City city);
    
	/** 
	 * 删除City
	 **/
    public void removeById(int id);
    
	/** 
	 * 根据ID得到City
	 **/    
    public City getById(int id);
    
    /** 
	 * 根据ID得到City,找不到抛异常
	 **/ 
    public City getRequiredById(int id);
    
	/** 
	 * 分页查询: City
	 **/      
	public Page<City> findPage(CityQuery query);
	
	public List<City> findList(CityQuery query);


	/** 
	 * 权限检查,在Controller或WebService层调用, 请自行实现
	 * @param userId 登录用户ID
	 * @throws SecurityException 没有权限时抛出 
	 **/
	public void checkPermission(long userId,City city,String permission);

}

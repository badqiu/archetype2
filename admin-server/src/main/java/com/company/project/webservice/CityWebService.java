/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.webservice;


import com.company.project.model.*;
import com.company.project.query.*;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;
import java.util.Date;
import java.util.List;

/**
 * [City] 的WebService接口
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0
 */
public interface CityWebService {

	/** 
	 * 创建City
	 **/
	public void create(City city);
	
	/** 
	 * 更新City
	 **/	
    public void update(City city);
    
	/** 
	 * 删除City
	 **/
    public void removeById(int id, int provinceId);
    
	/** 
	 * 根据ID得到City
	 **/    
    public City getById(boolean join,int id, int provinceId);
    
	/** 
	 * 分页查询: City
	 **/      
	public Page<City> findPage(boolean join,CityQuery query);
	
	/** 
	 * 搜索: City
	 **/ 
	public List<City> search(boolean join,String query,PageQuery pageQuery);
}

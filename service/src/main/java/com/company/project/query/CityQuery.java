/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.query;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import com.github.rapid.common.util.page.PageQuery;

/**
 * [City] 查询对象
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-28
 */
public class CityQuery extends PageQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	private Integer id;
	private Integer provinceId;
	private String cityName;
	private String description;

	/** 城市编号 */
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	/** 省份编号 */
	public Integer getProvinceId() {
		return this.provinceId;
	}
	
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	/** 城市名称 */
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	/** 描述 */
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}


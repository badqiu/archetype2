/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.model;

import javax.validation.constraints.*;
import java.util.*;
import org.hibernate.validator.constraints.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * tableName: city [City] 
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-12-13
 */
public class City  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//date formats
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 城市编号       db_column: id 
     */ 	
	@Max(9999999999L)
	private Integer id;
	
    /**
     * 省份编号       db_column: province_id 
     */ 	
	@NotNull @Max(9999999999L)
	private Integer provinceId;
	
    /**
     * 城市名称       db_column: city_name 
     */ 	
	@Length(max=25)
	private String cityName;
	
    /**
     * 描述       db_column: description 
     */ 	
	@Length(max=25)
	private String description;
	
	//columns END

	public City(){
	}

	public City(
		Integer id
	){
		this.id = id;
	}

	/**
     * 城市编号
     */ 
	@Id
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
     * 省份编号
     */ 
	public Integer getProvinceId() {
		return this.provinceId;
	}
	
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	/**
     * 城市名称
     */ 
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	/**
     * 描述
     */ 
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof City == false) return false;
		City other = (City)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}


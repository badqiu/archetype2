/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao.impl;

import com.company.project.model.*;
import com.company.project.query.*;
import com.company.project.dao.CityDao;















import java.io.Serializable;
import java.util.List;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.ObjectUtil;
import com.github.rapid.common.jdbc.dao.support.BaseSpringJdbcDao;
import com.github.rapid.common.jdbc.sqlgenerator.CacheSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.Table;

/**
 * tableName: city
 * [City] 的Dao操作 
 *  
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-28
*/
@Repository("cityDao")
@CacheConfig(cacheNames="city")
public class CityDaoImpl extends BaseSpringJdbcDao implements CityDao{

	protected static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);
	
	@Resource(name="demoprojectDataSource")
	private DataSource dataSource;
	
	/*
	* 请删除无用的方法，本代码生成器的理念是: 1. 一次生成，后期手工修改代码 2. 删除代码比手写重复代码快捷，所以请删除无用代码
	*/
	
	private RowMapper<City> entityRowMapper = new BeanPropertyRowMapper<City>(getEntityClass());
	
	private CacheSqlGenerator sqlGenerator = null; //增删改查sql生成工具
	private Table table;
	protected String columns = null; // 表的列，如:  age,sex
	protected String selectFromSql = null; // SQL: select age,sex from demo_table  
	
	@Override
	protected void checkDaoConfig() {
		setDataSource(dataSource);
		super.checkDaoConfig();
		
		table = MetadataCreateUtils.createTable(getEntityClass());
		sqlGenerator = new CacheSqlGenerator(new SpringNamedSqlGenerator(table));
		columns = sqlGenerator.getColumnsSql();
		selectFromSql = "select "+sqlGenerator.getColumnsSql()+" from " + table.getTableName()+" ";
	}
	
	@Override
	public Class<City> getEntityClass() {
		return City.class;
	}
	
	@Override
	public String getIdentifierPropertyName() {
		return "id";
	}
	
	public RowMapper<City> getEntityRowMapper() {
		return entityRowMapper;
	}
	
	@CacheEvict(key="#entity.id+'/'+#entity.provinceId")
	public void insert(City entity) {
		String sql = sqlGenerator.getInsertSql();
		insertWithGeneratedKey(entity,sql); //for sqlserver:identity and mysql:auto_increment
		
		//其它主键生成策略
		//insertWithOracleSequence(entity,"sequenceName",sql); //oracle sequence: 
		//insertWithDB2Sequence(entity,"sequenceName",sql); //db2 sequence:
		//insertWithUUID(entity,sql); //uuid
		//insertWithAssigned(entity,sql); //手工分配
	}
	
	@CacheEvict(key="#entity.id+'/'+#entity.provinceId")
	public int update(City entity) {
		String sql = sqlGenerator.getUpdateByPkSql();
		return getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(entity));
	}
	
	@CacheEvict(key="#id+'/'+#provinceId")
	public int deleteById(int id, int provinceId) {
		String sql = sqlGenerator.getDeleteByPkSql();
		return  getJdbcTemplate().update(sql,  id,provinceId);
	}

	@Cacheable(key="#id+'/'+#provinceId")
	public City getById(int id, int provinceId) {
//		String sql = sqlGenerator.getSelectByPkSql();
//		return (City)DataAccessUtils.singleResult(getJdbcTemplate().query(sql, getEntityRowMapper(),id,provinceId));
		String sql = "show tables";
		System.out.println(getJdbcTemplate().queryForList(sql));
		return null;
	}
	

	public Page<City> findPage(CityQuery query) {
		
		StringBuilder sql = getQuerySql(query);
		
        //sql.append(" order by :sortColumns ");
		
		return pageQuery(sql.toString(),query,getEntityRowMapper());				
	}
	
	public List<City> findList(CityQuery query) {
		StringBuilder sql = getQuerySql(query);
		return getNamedParameterJdbcTemplate().query(sql.toString(),new BeanPropertySqlParameterSource(query),getEntityRowMapper());
	}
	
	public StringBuilder getQuerySql(CityQuery query) {
		StringBuilder sql = new StringBuilder(selectFromSql + " where 1=1 ");
		
		//大表容易有性能问题，小表才能使用动态查询
		
		/*
		if(ObjectUtil.isNotEmpty(query.getId())) {
			sql.append(" and id = :id ");
		}
		if(ObjectUtil.isNotEmpty(query.getProvinceId())) {
			sql.append(" and province_id = :provinceId ");
		}
		if(ObjectUtil.isNotEmpty(query.getCityName())) {
			sql.append(" and city_name = :cityName ");
		}
		if(ObjectUtil.isNotEmpty(query.getDescription())) {
			sql.append(" and description = :description ");
		}
		*/
		
		return sql;
	}
}



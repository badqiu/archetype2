/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import com.company.project.dao.CityDao;
import com.company.project.model.City;
import com.github.rapid.common.jdbc.dao.support.BaseSpringJdbcDao;
import com.github.rapid.common.jdbc.sqlgenerator.CacheSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.Table;
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
	
	private CacheSqlGenerator sqlGenerator = null;
	private Table table;
	protected String columns = null; // 表的列，如:  age,sex
	protected String selectFromSql = null; // SQL: select age,sex from table  
	
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
	
	@CacheEvict(key="#entity.id")
	public void insert(City entity) {
		String sql = sqlGenerator.getInsertSql();
		insertWithGeneratedKey(entity,sql); //for sqlserver:identity and mysql:auto_increment
		
		//其它主键生成策略
		//insertWithOracleSequence(entity,"sequenceName",sql); //oracle sequence: 
		//insertWithDB2Sequence(entity,"sequenceName",sql); //db2 sequence:
		//insertWithUUID(entity,sql); //uuid
		//insertWithAssigned(entity,sql); //手工分配
	}
	
	@CacheEvict(key="#entity.id")
	public int update(City entity) {
		String sql = sqlGenerator.getUpdateByPkSql();
		return getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(entity));
	}
	
	@CacheEvict(key="#id")
	public int deleteById(int id) {
		String sql = sqlGenerator.getDeleteByPkSql();
		return  getJdbcTemplate().update(sql,  id);
	}

	@Cacheable(key="#id")
	public City getById(int id) {
		String sql = sqlGenerator.getSelectByPkSql();
		return (City)DataAccessUtils.singleResult(getJdbcTemplate().query(sql, getEntityRowMapper(),id));
	}
	
	
}



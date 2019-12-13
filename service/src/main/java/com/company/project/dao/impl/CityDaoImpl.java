/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.company.project.dao.CityDao;
import com.company.project.model.City;
import com.company.project.query.CityQuery;
import com.github.rapid.common.jdbc.sqlgenerator.CacheSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import com.github.rapid.common.jdbc.sqlgenerator.metadata.Table;
import com.github.rapid.common.util.page.Page;

/**
 * tableName: city
 * [City] 的Dao操作 
 *  
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-12-13
*/
@Repository("cityDao")
@CacheConfig(cacheNames="city")
public class CityDaoImpl extends BaseDao implements CityDao{

	protected static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);
	
	@Resource(name="demoprojectDataSource")
	private DataSource dataSource;
	
	/*
	* 请删除无用的方法，本代码生成器的理念是: 1. 一次生成，后期手工修改代码 2. 删除代码比手写重复代码快捷，所以请删除无用代码
	*/
	
	private RowMapper<City> entityRowMapper = new BeanPropertyRowMapper<City>(getEntityClass());
	
	protected CacheSqlGenerator sqlGenerator = null; //增删改查sql生成工具
	protected Table table;
	protected String selectFromSql = null; // SQL: select age,sex from demo_table  
	
	@Override
	protected void checkDaoConfig() {
		setDataSource(dataSource);
		super.checkDaoConfig();
		
		table = MetadataCreateUtils.createTable(getEntityClass());
		sqlGenerator = new CacheSqlGenerator(new SpringNamedSqlGenerator(table));
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
	
	//@CacheEvict(key="#entity.id")
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
		return getExtNamedJdbcTemplate().update(sql, entity);
	}
	
	@CacheEvict(key="#entity.id")
	public int deleteById(City entity) {
		String sql = sqlGenerator.getDeleteByPkSql();
		return  getExtNamedJdbcTemplate().update(sql,entity);
	}

	@Cacheable(key="#entity.id")
	public City getById(City entity) {
		String sql = sqlGenerator.getSelectByPkSql();
		return getExtNamedJdbcTemplate().queryOne(sql, entity,getEntityRowMapper());
	}
	

	public Page<City> findPage(CityQuery query) {
		
		StringBuilder sql = getQuerySql(query);
		
        //sql.append(" order by :sortColumns ");
		
		return pageQuery(sql.toString(),query,getEntityRowMapper());				
	}
	
	public List<City> findList(CityQuery query) {
		StringBuilder sql = getQuerySql(query);
		return getExtNamedJdbcTemplate().query(sql.toString(),query,getEntityRowMapper());
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



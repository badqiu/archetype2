package com.company.project.query;

import org.apache.commons.lang.StringUtils;

import com.github.rapid.common.util.page.PageQuery;

public class BaseQuery extends PageQuery{

	private static final long serialVersionUID = 1L;
	
	private String keyword;
	
	private String sortColumns;

	public BaseQuery() {
		super();
	}

	public BaseQuery(int page, int pageSize) {
		super(page, pageSize);
	}

	public BaseQuery(int pageSize) {
		super(pageSize);
	}

	public BaseQuery(PageQuery query) {
		super(query);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	public String getSortColumns() {
		return checkSqlSortColumns(sortColumns);
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public static String checkSqlSortColumns(String sortColumns) {
		if(StringUtils.isBlank(sortColumns)) {
			return null;
		}
		sortColumns = sortColumns.trim();
		
		String sortColumnsLowerCase = sortColumns.toLowerCase();
		if(sortColumnsLowerCase.contains("union")) {
			throw new RuntimeException("invalid sortColumns by invalid keyword:"+sortColumns);
		}
		
		if(sortColumns.matches("[a-zA-Z0-9_,\\s]+")) {
			return sortColumns;
		}
		
		throw new RuntimeException("invalid sortColumns:"+sortColumns);
	}
}

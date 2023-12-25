package com.company.project.query;

import com.github.rapid.common.util.page.PageQuery;

public class BaseQuery extends PageQuery{

	private static final long serialVersionUID = 1L;
	
	private String keyword;

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
	
}

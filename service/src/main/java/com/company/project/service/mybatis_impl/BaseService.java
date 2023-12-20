package com.company.project.service.mybatis_impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.service.Service;
import com.github.rapid.common.util.page.Page;
import com.github.rapid.common.util.page.PageQuery;
import com.github.rapid.common.util.page.Paginator;

public abstract class BaseService implements Service{
	
	public static <T> Page<T> toRapidPage(IPage<T> r) {
		Page page = new Page();
		page.setItemList(r.getRecords());
		page.setPaginator(toPaginator(r));
		return page;
	}

	public static Paginator toPaginator(IPage r) {
		return new Paginator((int)r.getCurrent(), (int)r.getSize(), (int)r.getTotal());
	}

	public static IPage<?> toMybatisPage(PageQuery query) {
		com.baomidou.mybatisplus.extension.plugins.pagination.Page r = new com.baomidou.mybatisplus.extension.plugins.pagination.Page();
		r.setSize(query.getPageSize());
		r.setCurrent(query.getPage());
		return r;
	}
}

package com.company.project.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	/**
	 * 检查实体的读写权限,没有权限抛出 SecurityException
	 * 
	 * @param entity 表实体对象，应用一条行记录
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public void checkEntityPermission(HttpServletRequest request,Object entity,String permission) {
	}
	
}

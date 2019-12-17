package com.company.project.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	/**
	 * 检查实体读写权限(数据行读写权限),没有权限抛出 SecurityException
	 * 
	 * @param entity 实体对象(数据行)
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	protected void checkEntityPermission(HttpServletRequest request,Object entity, String permission) {
		//检查读权限
		if("r".equals(permission)){
			return;
		}
		
		//检查写权限
		if("w".equals(permission)){
			/*
			示例代码,如发现创建人 == userId
		 	 if("w".equals(permission)) {
				 if(entity.getUserId() == userId) {
				 	return;
				 }
				 
				 throw new SecurityException("you cannot admin ${className}:"+${classNameLower});
			 }
			 */
			
			return;
		}
	}
	
}

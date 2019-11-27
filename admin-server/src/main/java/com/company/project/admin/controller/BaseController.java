package com.company.project.admin.controller;

import javax.servlet.http.HttpServletRequest;

import com.company.project.common.util.ActionSecurityUtil;

public class BaseController {

	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	protected void checkActionPermission(HttpServletRequest request,Class<?> actionType, String permission) {
		ActionSecurityUtil.checkActionPermission(request, actionType, permission);
	}
	
}

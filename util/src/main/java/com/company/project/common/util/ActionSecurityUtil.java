package com.company.project.common.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ActionSecurityUtil {

	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public static void checkActionPermission(HttpServletRequest request,Class<?> actionType,String permission) {
		checkActionPermission(request,actionType.getSimpleName().toLowerCase(),permission);
	}

	public static void checkActionPermission(HttpServletRequest request, String actionType,String permission) {
		long userId = getUserId(request);
		Set userPermissionSet = getUserPermissionSet(userId,request);
		
		if(userPermissionSet.contains(actionType+":"+permission)) {
			return;
		}else {
			throw new SecurityException("not permission,actionType:"+actionType+" permission:"+permission+" userId:"+userId);
		}
	}
	
	/** 请自行实现
	 *  得到用户拥有的权限集合 
	 **/
	private static Set getUserPermissionSet(long userId,HttpServletRequest request) {
		return (Set)request.getSession().getAttribute("LOGIN_USER_PERMISSION");
	}

	private static long getUserId(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute("LOGIN_USER_ID");
	}
	
}

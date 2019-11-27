package com.company.project.common.util;

import java.util.Set;


import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理权限检查，检查某类动作的读写权限
 * 
 * r: 读权限
 * w: 写权限(增删修改)
 * 
 * @author badqiu
 *
 */
public class ActionSecurityUtil {
	private static String LOGIN_USER_PERMISSION = "LOGIN_USER_PERMISSION";
	private static String LOGIN_USER_ID = "LOGIN_USER_ID";
	
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
		return (Set)request.getSession().getAttribute(LOGIN_USER_PERMISSION);
	}

	private static long getUserId(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(LOGIN_USER_ID);
	}
	
	/** 
	 *  用户登录成功,设置登录成功信息
	 *  @param userId 登录用户ID
	 *  @param userPermissionSet 登录用户的权限集合,格式示例为:  sometype:w , sometype:r
	 **/
	public static void setLoginUserInfo(HttpServletRequest request,long userId,Set userPermissionSet) {
		request.getSession().setAttribute(LOGIN_USER_ID,userId);
		request.getSession().setAttribute(LOGIN_USER_PERMISSION,userPermissionSet);
	}
	
}

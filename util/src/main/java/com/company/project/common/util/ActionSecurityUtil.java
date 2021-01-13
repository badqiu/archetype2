package com.company.project.common.util;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理权限检查，检查某类动作的读写权限
 * 
 * r: 读权限
 * w: 写权限(增删修改)
 * a: 管理权限
 * 
 * @author badqiu
 *
 */
public class ActionSecurityUtil {
	public static String READ = "r"; //读权限
	public static String WRITE = "w"; //写权限
	public static String ADMIN = "a"; //管理权限
	
	
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
		if(isIgnoreCheck(actionType,permission)) {
			return;
		}
				
		long userId = getLoginUserId(request);
		if(isSuperAdminUser(userId)) {
			return;
		}
		
		Set userPermissionSet = getUserPermissionSet(userId,request);
		
		if(userPermissionSet.contains(actionType)){
			return;
		}
		
		if(userPermissionSet.contains(actionType+":"+permission)) {
			return;
		}
		
		throw new SecurityException("not permission,actionType:"+actionType+" permission:"+permission+" userId:"+userId);
	}
	
	static Set<String> ignoreCheckActinoTypeList = new HashSet<String>();
	private static boolean isIgnoreCheck(String actionType, String permission) {
		//检查读权限
		if(READ.equals(permission)){
			return true;
		}
		if(ignoreCheckActinoTypeList.contains(actionType)) {
			return true;
		}
		
		return false;
	}

	private static boolean isSuperAdminUser(long userId) {
		return false;
	}

	/** 请自行实现
	 *  得到用户拥有的权限集合 
	 **/
	private static Set getUserPermissionSet(long userId,HttpServletRequest request) {
		Set set = (Set)request.getSession().getAttribute(LOGIN_USER_PERMISSION);
		return set == null ? new HashSet() : set;
	}

	public static long getLoginUserId(HttpServletRequest request) {
		Long userId = (Long)request.getSession().getAttribute(LOGIN_USER_ID);
		return userId == null ? -1 : userId;
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

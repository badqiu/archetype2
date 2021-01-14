package com.company.project.common.util;

import java.io.Serializable;
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
	
	private static String LOGIN_USER = "LOGIN_USER";
	
	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public static void checkActionPermission(HttpServletRequest request,Class<?> actionType,String permission) {
		checkActionPermission(request,toActionTypeString(actionType),permission);
	}

	public static void checkActionPermission(HttpServletRequest request, String actionType,String permission) {
		boolean hasActionPermission = hasActionPermission(request, actionType, permission);
		if(!hasActionPermission) {
			throw new SecurityException("not permission,actionType:"+actionType+" permission:"+permission);
		}
	}

	public static boolean hasActionPermission(HttpServletRequest request, Class<?> actionType, String permission) {
		return hasActionPermission(request,toActionTypeString(actionType),permission);
	}
	
	public static boolean hasActionPermission(HttpServletRequest request, String actionType, String permission) {
		if(isIgnoreCheck(actionType,permission)) {
			return true;
		}
				
		LoginUser loginUser = getLoginUser(request);
		if(isSuperAdminUser(loginUser)) {
			return true;
		}
		
		Set userPermissionSet = getUserPermissionSet(loginUser);
		
		if(userPermissionSet.contains(actionType)){
			return true;
		}
		
		if(userPermissionSet.contains(actionType+":"+ADMIN)) {
			return true;
		}
		
		if(userPermissionSet.contains(actionType+":"+permission)) {
			return true;
		}
		
		return false;
	}
	
	static Set<String> ignoreCheckActinoTypeList = new HashSet<String>();
	static {
		//增加忽略权限检查的对象
		//ignoreCheckActinoTypeList.add(toActionTypeString(TableDef.class));
	}
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

	private static boolean isSuperAdminUser(LoginUser loginUser) {
		if(loginUser.isSuperAdmin()) return true;
		return false;
	}

	private static String toActionTypeString(Class<?> actionType) {
		return actionType.getSimpleName().toLowerCase();
	}
	
	/** 
	 *  得到用户拥有的权限集合 
	 **/
	private static Set getUserPermissionSet(LoginUser loginUser) {
		Set set = (Set)loginUser.getUserPermissionSet();
		return set == null ? new HashSet() : set;
	}

	public static LoginUser getLoginUser(HttpServletRequest request) {
		LoginUser user = (LoginUser)request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
	
	/** 
	 *  用户登录成功,设置登录成功信息
	 *  @param userId 登录用户ID
	 *  @param superAdmin 是否超级管理员
	 *  @param userPermissionSet 登录用户的权限集合,格式示例为:  sometype:w , sometype:r
	 **/
	public static void setLoginUser(HttpServletRequest request,LoginUser loginUser) {
		request.getSession().setAttribute(LOGIN_USER,loginUser);
	}
	
}

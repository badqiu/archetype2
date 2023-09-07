package com.company.project.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	
	public static String READ = PermissionType.READ.getShortName(); 
	public static String WRITE = PermissionType.WRITE.getShortName(); 
	public static String ADMIN = PermissionType.ADMIN.getShortName(); 
	
	private static String LOGIN_USER = "LOGIN_USER";
	
	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public static void checkActionPermission(HttpServletRequest request,Class<?> actionType,String permission) {
		LoginUser loginUser = getRequiredLoginUser(request);
		loginUser.checkPermission(actionType, permission);
	}

	public static void checkActionPermission(HttpServletRequest request, String actionType,String permission) {
		LoginUser loginUser = getRequiredLoginUser(request);
		loginUser.checkPermission(actionType, permission);
	}

	public static boolean hasActionPermission(HttpServletRequest request, Class<?> actionType, String permission) {
		LoginUser loginUser = getLoginUser(request);
		return loginUser == null ? false : loginUser.hasPermission(actionType, permission);
	}
	
	public static boolean hasActionPermission(HttpServletRequest request, String actionType, String permission) {
		LoginUser loginUser = getLoginUser(request);
		return loginUser == null ? false : loginUser.hasPermission(actionType, permission);
	}
	
	public static LoginUser getRequiredLoginUser(HttpServletRequest request) {
		LoginUser user = getLoginUser(request);
		if(user == null) {
			throw new NeedLoginException("not yet login");
		}
		return user;
	}
	
	public static LoginUser getLoginUser(HttpServletRequest request) {
		LoginUser user = (LoginUser)request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
	
	/** 
	 *  用户登录成功,设置登录成功信息
	 **/
	public static void setLoginUser(HttpServletRequest request,LoginUser loginUser) {
		request.getSession().setAttribute(LOGIN_USER,loginUser);
	}
	
	public static void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.removeAttribute(LOGIN_USER);
		}
	}
	
}

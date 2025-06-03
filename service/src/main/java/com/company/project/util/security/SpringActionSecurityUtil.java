package com.company.project.util.security;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class SpringActionSecurityUtil extends ActionSecurityUtil {
	
	public static ServletRequestAttributes getContextServletRequestAttributes() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes;
	}
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attr = getContextServletRequestAttributes();
		if(attr == null) return null;
		
		return attr.getRequest();
	}
	
	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public static void checkActionPermission(Class<?> actionType,String permission) {
		checkActionPermission(getRequest(),actionType,permission);
	}

	public static void checkActionPermission(String actionType,String permission) {
		checkActionPermission(getRequest(),actionType,permission);
	}

	public static boolean hasActionPermission(Class<?> actionType, String permission) {
		return hasActionPermission(getRequest(), actionType, permission);
	}
	
	public static boolean hasActionPermission(String actionType, String permission) {
		return hasActionPermission(getRequest(), actionType, permission);
	}
	
	public static LoginUser getRequiredLoginUser() {
		return getRequiredLoginUser(getRequest());
	}
	
	public static LoginUser getLoginUser() {
		return getLoginUser(getRequest());
	}
	
	/** 
	 *  用户登录成功,设置登录成功信息
	 **/
	public static void setLoginUser(LoginUser loginUser) {
		setLoginUser(getRequest(),loginUser);
	}
	
	public static void logout() {
		logout(getRequest());
	}
	
}

package com.company.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.project.common.util.ActionSecurityUtil;

public class BaseController {

	public static String READ = ActionSecurityUtil.READ; //读权限
	public static String WRITE = ActionSecurityUtil.WRITE; //写权限
	public static String ADMIN = ActionSecurityUtil.ADMIN; //管理权限
	
	/**
	 * 检查动作读写权限(表读写权限),没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	protected void checkActionPermission(HttpServletRequest request,Class<?> actionType, String permission) {
		ActionSecurityUtil.checkActionPermission(request, actionType, permission);
	}
	
	/**
	 * 检查实体读写权限(数据行读写权限),没有权限抛出 SecurityException
	 * 
	 * @param entity 实体对象(数据行)
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	protected void checkEntityPermission(HttpServletRequest request,Object entity, String permission) {
		ActionSecurityUtil.checkActionPermission(request, entity.getClass(), permission);
		
//		//检查读权限
//		if(READ.equals(permission)){
//			return;
//		}
//		
//		//检查写权限
//		if(WRITE.equals(permission)){
//			/*
//			示例代码,如发现创建人 == userId
//		 	 if(WRITE.equals(permission)) {
//				 if(entity.getUserId() == userId) {
//				 	return;
//				 }
//				 
//				 throw new SecurityException("you cannot admin ${className}:"+${classNameLower});
//			 }
//			 */
//			
//			return;
//		}
	}
	
	protected long getLoginUserId(HttpServletRequest request) {
		return ActionSecurityUtil.getLoginUserId(request);
	}
	
	public static HttpServletRequest getRequest() {
		return getContextServletRequestAttributes().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return getContextServletRequestAttributes().getResponse();
	}

	public static ServletRequestAttributes getContextServletRequestAttributes() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes;
	}
	
}

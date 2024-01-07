package com.company.project.controller;

import java.util.HashSet;
import java.util.Set;

import javax.naming.event.ObjectChangeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.project.common.security.ActionSecurityUtil;
import com.company.project.common.security.LoginUser;
import com.company.project.common.security.NeedLoginException;

public abstract class BaseController {

	public static String READ = ActionSecurityUtil.READ; //读权限
	public static String WRITE = ActionSecurityUtil.WRITE; //写权限
	public static String CREATE = ActionSecurityUtil.CREATE; //创建权限
	public static String UPDATE = ActionSecurityUtil.UPDATE; //更新权限
	public static String DELETE = ActionSecurityUtil.DELETE; //删除权限
	public static String EXECUTE = ActionSecurityUtil.EXECUTE; //运行权限
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
		if(isEntityPermissionCheck(entity,permission)) {
			checkEntityPermission0(request,entity,permission);
		}else {
			ActionSecurityUtil.checkActionPermission(request, entity.getClass(), permission);
		}
	}
	
	private void checkEntityPermission0(HttpServletRequest request, Object entity, String permission) {
	}

	Set<Class> entityPermissionCheck = new HashSet<Class>();
	{
		entityPermissionCheck.add(ObjectChangeListener.class);
	}
	private boolean isEntityPermissionCheck(Object entity, String permission) {
		return entityPermissionCheck.contains(entity.getClass());
	}

	public static LoginUser getLoginUser(HttpServletRequest request) {
		return ActionSecurityUtil.getLoginUser(request);
	}
	
	public static Long getLoginUserId(HttpServletRequest request) {
		LoginUser<Long> loginUser = ActionSecurityUtil.getLoginUser(request);
		if(loginUser == null) {
			return null;
		}
		return loginUser.getUserId();
	}
	
	public static Long getLoginUserId() {
		return getLoginUserId(getRequest());
	}
	
	public static Long getRequiredLoginUserId(HttpServletRequest request) {
		LoginUser<Long> loginUser = ActionSecurityUtil.getLoginUser(request);
		if(loginUser == null) {
			throw new NeedLoginException();
		}
		return loginUser.getUserId();
	}
	
	public static Long getRequiredLoginUserId() {
		return getRequiredLoginUserId(getRequest());
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

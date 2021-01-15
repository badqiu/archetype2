package com.company.project.common.util;

import java.io.Serializable;
import java.security.AccessControlException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/** 登录用户信息 */
public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String username;
	private boolean superAdmin; // 是否超级管理员
	private Set<String> userPermissionSet = new HashSet<String>(); // 用户拥有的权限
	private Date loginTime = new Date();
//		private Set<String> userRoleSet; //用户拥有的角色

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Set<String> getUserPermissionSet() {
		return userPermissionSet;
	}

	public void setUserPermissionSet(Set<String> userPermissionSet) {
		this.userPermissionSet = userPermissionSet;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public LoginUser addPermission(Class<?> actionType,String permission) {
		addPermission(toActionTypeString(actionType),permission);
		return this;
	}
	
	public LoginUser addPermission(String actionType,String permission) {
		userPermissionSet.add(toPermissionString(actionType, permission));
		return this;
	}

	public boolean hasPermission(Class<?> actionType,String permission) {
		return hasPermission(toActionTypeString(actionType),permission);
	}
	
	public boolean hasPermission(String actionType,String permission) {
		if(superAdmin) return true;
		if(isIgnoreCheck(actionType, permission)) return true;
		
		
		if(userPermissionSet.contains(actionType)){
			return true;
		}
		
		if(userPermissionSet.contains(toPermissionString(actionType,ActionSecurityUtil.ADMIN))) {
			return true;
		}
		
		if(userPermissionSet.contains(toPermissionString(actionType, permission))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查读写权限,没有权限抛出 SecurityException
	 * 
	 * @param actionType 动作分类
	 * @param permission 增删修改=w(写权限), 查=r(读权限)
	 */
	public void checkPermission(Class<?> actionType,String permission) {
		checkPermission(toActionTypeString(actionType),permission);
	}

	public void checkPermission(String actionType,String permission) {
		boolean hasActionPermission = hasPermission(actionType, permission);
		if(!hasActionPermission) {
			throw new AccessControlException("not permission,actionType:"+actionType+" permission:"+permission);
		}
	}

	public static String toActionTypeString(Class<?> actionType) {
		return actionType.getSimpleName().toLowerCase();
	}
	
	protected String toPermissionString(String actionType, String permission) {
		return actionType+":"+permission;
	}
	
//		public Set<String> getUserRoleSet() {
//			return userRoleSet;
//		}
//		public void setUserRoleSet(Set<String> userRoleSet) {
//			this.userRoleSet = userRoleSet;
//		}

	public static Set<String> ignoreCheckActinoTypeList = new HashSet<String>();
	static {
		//增加忽略权限检查的对象
		//ignoreCheckActinoTypeList.add(toActionTypeString(TableDef.class));
	}
	private static boolean isIgnoreCheck(String actionType, String permission) {
		//检查读权限
		if(ActionSecurityUtil.READ.equals(permission)){
			return true;
		}
		if(ignoreCheckActinoTypeList.contains(actionType)) {
			return true;
		}
		
		return false;
	}
	
}
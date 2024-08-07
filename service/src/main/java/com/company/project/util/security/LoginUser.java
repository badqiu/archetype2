package com.company.project.util.security;

import java.io.Serializable;
import java.security.AccessControlException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** 登录用户信息 */
public class LoginUser <USERID> implements Serializable {
	private static final long serialVersionUID = 1L;

	private USERID userId; //登录用户ID
	private String username; //登录用户名
	private boolean superAdmin; // 是否超级管理员
	private Date loginTime = new Date(); //登录时间
	
	private Set<String> userPermissionSet = new HashSet<String>(0); // 用户拥有的权限
	private Set<String> userRoleSet = new HashSet<String>(0); //用户拥有的角色
	private Set<String> userDeptSet = new HashSet<String>(0); //用户拥有的部门

	public USERID getUserId() {
		return userId;
	}

	public void setUserId(USERID userId) {
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
	
	public boolean hasRole(String role) {
		if(superAdmin) return true;
		return userRoleSet.contains(role);
	}
	
	public boolean hasDept(String dept) {
		if(superAdmin) return true;
		return userDeptSet.contains(dept);
	}
	
	public void checkRole(String role) {
		if(!hasRole(role)) {
			throw new AccessControlException("not permission,must be has role:"+role);
		}
	}
	
	public void checkDept(String dept) {
		if(!hasRole(dept)) {
			throw new AccessControlException("not permission,must be has dept:"+dept);
		}
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

	public String toActionTypeString(Class<?> actionType) {
		return actionType.getSimpleName().toLowerCase();
	}
	
	public String toPermissionString(String actionType, String permission) {
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
	
	protected boolean isIgnoreCheck(String actionType, String permission) {
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
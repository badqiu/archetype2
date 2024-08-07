package com.company.project.util.security;

/**
 *	权限类型
 */
public enum PermissionType {
	READ("r","读权限"),  // 等同于read,select,access,get,query,search,receive,consume
	WRITE("w","写权限"), // 等同于create,add,insert,delete,remove,clear,update,edit,change,set,write,modify,produce,copy
	EXECUTE("x","运行权限"), // 等同于execute,run,deploy,stop,force_stop

	CREATE("c","创建权限"), // 等同于create,add,insert
	DELETE("d","删除权限"), // 等同于delete,clear,remove
	UPDATE("u","更新权限"), // 等同于update,edit,change,set,write,modify,copy

	ADMIN("a","管理权限"); // 等同于admin,manage,all,其它权限也可以包含进来(rollback,connect,listen)
	
	private final String shortName;
	private final String remarks;
	
	PermissionType(String shortName,String remarks) {
		this.shortName = shortName;
		this.remarks = remarks;
	}

	public String getShortName() {
		return shortName;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public boolean hasPermission(PermissionType p) {
		if(p == null) {
			return false;
		}
		
		if(p == ADMIN) {
			return true;
		}
		
		if(this == PermissionType.WRITE) {
			if(isWritePermission(p)) {
				return true;
			}
		}
		
		if(p == this) {
			return true;
		}
		return false;
	}

	public static boolean isWritePermission(PermissionType p) {
		return p == CREATE || p == DELETE || p == UPDATE || p == WRITE;
	}
}
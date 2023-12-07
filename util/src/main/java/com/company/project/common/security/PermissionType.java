package com.company.project.common.security;

/**
 *	权限类型
 */
public enum PermissionType {
	READ("r","读权限"),  // 等同于read,select,access,get,query,search,receive,consume
	WRITE("w","写权限"), // 等同于create,add,insert,delete,remove,clear,update,edit,change,set,write,modify,produce,copy
	EXECUTE("x","运行权限"), // 等同于execute,run,deploy,stop,force_stop
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
		
		if(p == PermissionType.ADMIN) {
			return true;
		}
		if(p == this) {
			return true;
		}
		return false;
	}
}
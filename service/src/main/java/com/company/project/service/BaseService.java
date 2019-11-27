package com.company.project.service;


public class BaseService {

	/**
	 * 
	 *  检查用户对行数据 (实体对象) 相关权限
	 *
	 */
	public void checkEntityPermission(long userId,Object entity,String permission){
		//检查读权限
		if("r".equals(permission)){
			return;
		}
		
		//检查写权限
		if("w".equals(permission)){
			/*
			示例代码,如发现创建人 == userId
		 	 if("w".equals(permission)) {
				 if(entity.getUserId() == userId) {
				 	return;
				 }
				 
				 throw new SecurityException("you cannot admin ${className}:"+${classNameLower});
			 }
			 */
			
			return;
		}
		
		
	}
	
}


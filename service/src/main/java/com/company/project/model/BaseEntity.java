package com.company.project.model;

import java.io.Serializable;

import javax.management.RuntimeErrorException;
/**
 * 基本实值，可以添加统一的审计信息，如: creator,operator,updateTime,createTime等字段
 * 
 * @author badqiu
 *
 */
public class BaseEntity implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
	

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("clone() error:"+e,e);
		}
	}
}


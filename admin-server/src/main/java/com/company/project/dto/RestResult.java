package com.company.project.dto;

/**
 * REST統一包裝結果
 * 
 * @author badqiu
 *
 */
public class RestResult {

	String SUCCESS_CODE = "success";
	String FAIL_CODE = "fail";
	
	private String code;
	
	private String msg;
	
	private Object result;
	
	public RestResult() {
		success();
	}
	
	public RestResult result(Object object) {
		this.result = object;
		return this;
	}
	
	public RestResult success() {
		this.code = SUCCESS_CODE;
		return this;
	}
	
	public RestResult fail(String msg) {
		this.code = FAIL_CODE;
		this.msg = msg;
		return this;
	}
	
	public RestResult fail(Exception error) {
		this.code = FAIL_CODE;
		this.msg = error.getMessage();
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Object getResult() {
		return result;
	}
	
}

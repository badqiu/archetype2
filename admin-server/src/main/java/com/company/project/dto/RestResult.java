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
	
	private String code; //成功，失败标识
	
	private String msg; //消息
	private String errCode; //错误码
	
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
	
	public RestResult errCode(String errCode) {
		this.errCode = errCode;
		return this;
	}
	
	public RestResult fail(Exception error) {
		this.code = FAIL_CODE;
		errCode(error.getClass().getSimpleName());
		this.msg = error.getMessage();
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public Object getResult() {
		return result;
	}
	
}

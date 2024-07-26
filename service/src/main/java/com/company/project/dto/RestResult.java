package com.company.project.dto;

/**
 * REST統一包裝結果
 * 
 * @author badqiu
 *
 */
public class RestResult {

	int SUCCESS_CODE = 0;
	int FAIL_CODE = 500;
	
	private int code; //成功，失败标识
	
	private String msg; //消息
	private String errCode; //错误码
	
	private Object data;
	
	
	private String traceId;
	private String errorLog;
	private boolean success;
	
	public RestResult() {
		success();
	}
	
	public RestResult data(Object object) {
		this.data = object;
		return this;
	}
	
	public RestResult success() {
		this.code = SUCCESS_CODE;
		this.success = true;
		return this;
	}
	
	public RestResult fail(String msg) {
		this.code = FAIL_CODE;
		this.success = false;
		this.msg = msg;
		return this;
	}
	
	public RestResult errCode(String errCode) {
		this.errCode = errCode;
		return this;
	}
	
	public RestResult fail(Exception error) {
		this.code = FAIL_CODE;
		this.success = false;
		errCode(error.getClass().getSimpleName());
		this.msg = error.getMessage();
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public Object getData() {
		return data;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public static RestResult success(Object data) {
		return new RestResult().data(data).success();
	}
	
}

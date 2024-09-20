package com.company.project.dto;

/**
 * REST統一包裝結果
 * 
 * @author badqiu
 *
 */
public class RestResult {

	static final int SUCCESS_CODE = 0;
	static final int FAIL_CODE = 500;
	
	private int code; //成功，失败标识
	
	private String msg; //消息
	private String errCode; //错误码
	
	private Object data; //返回数据
	
	private String traceId; //跟踪ID，方便查询日志
	private String errorLog; //错误日志，填充完整的错误堆栈信息
	private boolean success; //是否成功，客户端更容易判断
	
	public RestResult() {
		success();
	}
	
	public RestResult data(Object object) {
		this.data = object;
		return this;
	}
	
	public RestResult success() {
		code(SUCCESS_CODE);
		return this;
	}
	
	public RestResult fail(String msg) {
		code(FAIL_CODE);
		this.msg = msg;
		return this;
	}
	
	public RestResult fail(Exception error) {
		code(FAIL_CODE);
		errCode(error.getClass().getSimpleName());
		this.msg = error.getMessage();
		return this;
	}

	public RestResult errCode(String errCode) {
		this.errCode = errCode;
		return this;
	}
	
	public RestResult code(int code) {
		this.code = code;
		if(code == SUCCESS_CODE) {
			success = true;
		}else {
			success = false;
		}
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
	
	public void setMsg(String msg) {
		this.msg = msg;
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

package com.company.project.dto;

/**
 * 批量执行结果
 */
public class BatchResultStat {
	
	int successCount;
	int errorCount;
	Exception lastException;
	
	public BatchResultStat() {
	}
	
	public BatchResultStat(int successCount, int errorCount) {
		this.successCount = successCount;
		this.errorCount = errorCount;
	}

	public int getSuccessCount() {
		return successCount;
	}
	
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	public int getErrorCount() {
		return errorCount;
	}
	
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public Exception getLastException() {
		return lastException;
	}

	public void setLastException(Exception lastException) {
		this.lastException = lastException;
	}
	
}
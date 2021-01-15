package com.company.project.common.util;

public class NeedLoginException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public NeedLoginException() {
		super();
	}

	public NeedLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public NeedLoginException(String s) {
		super(s);
	}

	public NeedLoginException(Throwable cause) {
		super(cause);
	}

}

package com.cd.zj.server.exception;

public class InspectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InspectionException() {
	}

	public InspectionException(String message) {
		super(message);
	}

	public InspectionException(Throwable cause) {
		super(cause);
	}
}
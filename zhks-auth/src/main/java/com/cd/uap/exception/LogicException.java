package com.cd.uap.exception;

public class LogicException extends Exception {

	private static final long serialVersionUID = 1L;

	public LogicException() {
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(Throwable cause) {
		super(cause);
	}
}
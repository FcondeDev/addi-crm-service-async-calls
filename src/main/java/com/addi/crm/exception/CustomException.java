package com.addi.crm.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -6628372263603848109L;
	private final HttpStatus httpStatus;
	private final ErrorEnum errorEnum;

	public CustomException(ErrorEnum errorEnum, HttpStatus httpStatus) {
		this.errorEnum = errorEnum;
		this.httpStatus = httpStatus;

	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ErrorEnum getErrorEnum() {
		return errorEnum;
	}

}

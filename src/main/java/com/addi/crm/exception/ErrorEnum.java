package com.addi.crm.exception;

public enum ErrorEnum {

	NOT_FOUND_EXCEPTION(1, "NOT FOUND", "RESOURCE NOT FOUND"), FEIGN_ERROR(2, "FEIGN ERROR", "FEIGN ERROR"),
	ERROR_GETTING_INFORMATION_FROM_ASYNC(3, "ERROR GETTING INFORMATION",
			"ERROR GETTING INFORMATION FROM ASYNC METHODS");

	public final int code;
	public final String title;
	public final String description;

	ErrorEnum(int code, String title, String description) {
		this.code = code;
		this.title = title;
		this.description = description;
	}

}

package com.addi.crm.dto;

public class CrmResponseDTO {

	private String message;
	private boolean success;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public CrmResponseDTO(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

}

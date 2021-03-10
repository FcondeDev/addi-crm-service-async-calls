package com.addi.crm.dto;

public class NationalRegistrySimulationResponseDTO {

	private boolean success;
	private String failureReason;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public NationalRegistrySimulationResponseDTO(boolean success, String failureReason) {
		this.success = success;
		this.failureReason = failureReason;
	}

}

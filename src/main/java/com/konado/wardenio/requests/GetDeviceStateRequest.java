package com.konado.wardenio.requests;

public class GetDeviceStateRequest {

	private Long examTakenId;
	private String accessPin;
	private String customerPassword;
	private String customerUsername;

	public Long getExamTakenId() {
		return examTakenId;
	}

	public void setExamTakenId(Long examTakenId) {
		this.examTakenId = examTakenId;
	}

	public String getAccessPin() {
		return accessPin;
	}

	public void setAccessPin(String accessPin) {
		this.accessPin = accessPin;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

}

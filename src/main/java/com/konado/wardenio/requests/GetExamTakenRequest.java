package com.konado.wardenio.requests;

public class GetExamTakenRequest {

	private String customerPassword;

	private String customerUsername;

	private Long examTakenId;

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

	public Long getExamTakenId() {
		return examTakenId;
	}

	public void setExamTakenId(Long examTakenId) {
		this.examTakenId = examTakenId;
	}

}

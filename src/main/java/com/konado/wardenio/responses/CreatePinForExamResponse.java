package com.konado.wardenio.responses;

public class CreatePinForExamResponse {

	private Long examTakenId;

	private String accessPin;

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

}

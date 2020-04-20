package com.konado.wardenio.requests;

public class SetExamDesktopStateRequest {

	private Long examTakenId;
	private String accessPin;
	private String desktopType;
	private String desktopModel;
	private String desktopState;

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

	public String getDesktopType() {
		return desktopType;
	}

	public void setDesktopType(String desktopType) {
		this.desktopType = desktopType;
	}

	public String getDesktopModel() {
		return desktopModel;
	}

	public void setDesktopModel(String desktopModel) {
		this.desktopModel = desktopModel;
	}

	public String getDesktopState() {
		return desktopState;
	}

	public void setDesktopState(String desktopState) {
		this.desktopState = desktopState;
	}

}

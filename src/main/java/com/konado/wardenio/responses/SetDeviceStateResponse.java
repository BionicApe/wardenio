package com.konado.wardenio.responses;

public class SetDeviceStateResponse {

	private Long deviceStateId;
	public String message = "State updated successfully";

	public SetDeviceStateResponse() {
		super();
	}

	public SetDeviceStateResponse(Long deviceStateId) {
		super();
		this.deviceStateId = deviceStateId;
	}

	public Long getDeviceStateId() {
		return deviceStateId;
	}

	public void setDeviceStateId(Long deviceStateId) {
		this.deviceStateId = deviceStateId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

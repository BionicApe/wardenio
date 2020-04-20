package com.konado.wardenio.requests;

import com.amazonaws.util.StringUtils;

public class SetDeviceStateRequest {

	private Long examTakenId;
	private String accessPin;
	private String deviceType;
	private String deviceModel;
	private String deviceState;

	public Long getExamTakenId() {
		return examTakenId;
	}

	public void setExamTakenId(Long examTakenId) {
		this.examTakenId = examTakenId;
	}

	public String getAccessPin() {
		return accessPin;
	}

	public void setAccessPin(String pin) {
		this.accessPin = pin;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String mobileType) {
		this.deviceType = mobileType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String mobileModel) {
		this.deviceModel = mobileModel;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String mobileState) {
		this.deviceState = mobileState;
	}

	public void validate() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		boolean hasErrors = false;
		if (StringUtils.isNullOrEmpty(deviceType))
		{
			hasErrors = true;
			sb.append("Invalid: deviceType ");
		}
		if (StringUtils.isNullOrEmpty(deviceModel))
		{
			hasErrors = true;
			sb.append("Invalid: deviceModel ");
		}
		if (StringUtils.isNullOrEmpty(deviceState))
		{
			hasErrors = true;
			sb.append("Invalid: deviceState ");
		}
				if (hasErrors) {
			throw new Exception(sb.toString());
		}
	}
}

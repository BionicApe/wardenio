package com.konado.wardenio.responses;

import java.util.Date;
import java.util.List;

import com.konado.wardenio.model.DeviceState;

public class GetDeviceStateResponse {

	private Date timestamp;
	private Date nowDate = new Date();
	private List<DeviceState> deviceStateList;

	public List<DeviceState> getDeviceStateList() {
		return deviceStateList;
	}

	public void setDeviceStateList(List<DeviceState> deviceStateList) {
		this.deviceStateList = deviceStateList;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

}

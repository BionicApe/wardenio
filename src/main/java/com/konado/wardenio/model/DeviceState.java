package com.konado.wardenio.model;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceState {

	@SerializedName("deviceStateId")
	private Long id;
	@Expose(serialize = false, deserialize = false)
	private Long examTakenId;
	private String deviceType;
	private String deviceModel;

	/**
	 * ACCESS_GRANTED Tras introducir satisfactoriamente el pin
	 * 
	 * USER_ID_SENT Tras mandar las fotos de selfie y del DNI
	 * 
	 * STREAMING Cuando el dispositivo está mandando satisfactoriamente un video
	 * stream.
	 * 
	 * SENDING_ADDITIONAL_DATA:Cuando el examen ha finalizado y se está enviando
	 * contenido extra como imágnes del examen.
	 * 
	 * FINISHED Cuando el examen ha finalizado por completo.
	 **/
	private String deviceState;
	@Expose(serialize = true, deserialize = true)
	private Date timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public Long getExamTakenId() {
		return examTakenId;
	}

	public void setExamTakenId(Long examTakenId) {
		this.examTakenId = examTakenId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date date) {
		this.timestamp = date;
	}

}

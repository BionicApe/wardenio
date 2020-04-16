package com.konado.wardenio.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mysql.cj.util.StringUtils;

public class ExamTaken {

	@SerializedName("examTakenId")
	private Long id;
	private String examData;
	private Long eventId;
	private String accessPin;
	private String studentName;
	private String studentCode;
	private String bucketName;
	private String streamName;
	private String desktopStreamName;
	private Date startDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamData() {
		return examData;
	}

	public void setExamData(String examData) {
		this.examData = examData;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getAccessPin() {
		return accessPin;
	}

	public void setAccessPin(String accessPin) {
		this.accessPin = accessPin;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public boolean hasStarted() {
		return !(StringUtils.isNullOrEmpty(bucketName) && StringUtils.isNullOrEmpty(streamName));
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getDesktopStreamName() {
		return desktopStreamName;
	}

	public void setDesktopStreamName(String desktopStreamName) {
		this.desktopStreamName = desktopStreamName;
	}
}

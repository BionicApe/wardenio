package com.konado.wardenio.responses;

import java.util.Date;

public class GetExamTakenResponse {

	private Long examTakenId;
	private String studentName;
	private String bucketName;
	private String streamName;
	private Date startDate;
	private String examData;

	public Long getExamTakenId() {
		return examTakenId;
	}

	public void setExamTakenId(Long examTakenId) {
		this.examTakenId = examTakenId;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getExamData() {
		return examData;
	}

	public void setExamData(String examData) {
		this.examData = examData;
	}

}

package com.konado.wardenio.responses;

import java.util.Date;

import com.konado.wardenio.model.ExamTaken;

public class HasExamStartedResponse {

	private boolean hasStarted;
	private Date startDate;

	public boolean hasStarted() {
		return hasStarted;
	}

	public void setHasStarted(boolean hasStarted) {
		this.hasStarted = hasStarted;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isHasStarted() {
		return hasStarted;
	}

	public void setValuesFromExamTaken(ExamTaken examTaken) {
		hasStarted = examTaken.hasStarted();
		startDate = examTaken.getStartDate();
	}

}

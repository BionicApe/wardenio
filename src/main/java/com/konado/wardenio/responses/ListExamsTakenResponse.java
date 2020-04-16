package com.konado.wardenio.responses;

import java.util.List;

import com.konado.wardenio.model.Event;
import com.konado.wardenio.model.ExamTaken;

public class ListExamsTakenResponse {

	private Event event;
	
	private List<ExamTaken> examsTakenList;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<ExamTaken> getExamsTakenList() {
		return examsTakenList;
	}

	public void setExamsTakenList(List<ExamTaken> examsTakenList) {
		this.examsTakenList = examsTakenList;
	}

}

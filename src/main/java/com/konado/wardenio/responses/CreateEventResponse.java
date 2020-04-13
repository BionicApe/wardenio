package com.konado.wardenio.responses;

public class CreateEventResponse {

	private Long eventId;

	private String eventData;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}
}

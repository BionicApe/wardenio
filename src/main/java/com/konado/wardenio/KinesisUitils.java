package com.konado.wardenio;

import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.ExamTaken;

public class KinesisUitils {

	public static void createAndSetStreamName(Customer customer, ExamTaken examTaken) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(customer.getStreamName());
		sb.append("-");
		sb.append("exam-");
		sb.append(examTaken.getEventId());
		sb.append("-");
		sb.append(examTaken.getId());
		sb.append("-");
		sb.append(WardenioStringTools.generateRandomCode(2));
		examTaken.setStreamName(sb.toString());
	}

}

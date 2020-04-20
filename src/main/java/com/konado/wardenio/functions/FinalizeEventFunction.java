package com.konado.wardenio.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.konado.wardenio.WardenioUtils;
import com.konado.wardenio.dao.CustomerDao;
import com.konado.wardenio.dao.EventDao;
import com.konado.wardenio.dao.ExamTakenDao;
import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.Event;
import com.konado.wardenio.requests.FinalizeEventRequest;
import com.konado.wardenio.responses.FinalizeEventResponse;

public class FinalizeEventFunction implements RequestStreamHandler {

	private static CustomerDao customerDao = new CustomerDao();
	private static ExamTakenDao examTakenDao = new ExamTakenDao();
	private static EventDao eventDao = new EventDao();
	private static final Gson GSON = WardenioUtils.GSON;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject responseBody = new JsonObject();
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		String responseStr = "";

		try {

			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);
			FinalizeEventRequest request = GSON.fromJson((String) jsonRequest.get("body").getAsString(), FinalizeEventRequest.class);

			Customer customer = customerDao.GetByUsernameAndPassword(request.getCustomerUsername(), request.getCustomerPassword());
			if (customer == null) {
				throw new Exception("Customer not found");
			}

			Event event = eventDao.get(request.getEventId());
			if (event == null) {
				throw new Exception("Event not found");
			}
			if (event.getCustomerId() != customer.getId()) {
				throw new Exception("This event does not belong to the customer");
			}

			examTakenDao.invalidateAll(event);
			
			FinalizeEventResponse response = new FinalizeEventResponse();
			responseBody.add("response", GSON.toJsonTree(response));
			responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
		} catch (Throwable e) {
			logger.log(e.getMessage());
			responseStr = WardenioUtils.createUnsuccessfulResponse(responseBody, e);
		} finally {
			writer.write(responseStr);
			writer.close();
		}
	}
}

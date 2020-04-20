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
import com.konado.wardenio.model.ExamTaken;
import com.konado.wardenio.requests.HasExamStartedRequest;
import com.konado.wardenio.responses.HasExamStartedResponse;

public class HasExamStartedFunction implements RequestStreamHandler {

	private static final CustomerDao customerDao = new CustomerDao();
	private static final ExamTakenDao examTakenDao = new ExamTakenDao();
	private static final EventDao eventDao = new EventDao();
	private static final Gson GSON = WardenioUtils.GSON;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject responseBody = new JsonObject();
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");

		try {
			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);
			HasExamStartedRequest request = GSON.fromJson((String) jsonRequest.get("body").getAsString(), HasExamStartedRequest.class);


			Customer customer = customerDao.GetByUsernameAndPassword(request.getCustomerUsername(), request.getCustomerPassword());
			if (customer == null) {
				throw new Exception("Customer not found");
			}

			ExamTaken examTaken = examTakenDao.get(request.getExamTakenId());
			if (examTaken == null) {
				throw new Exception("ExamTaken not found");
			}

			Event event = eventDao.get(examTaken.getEventId());
			if (event == null) {
				throw new Exception("Event not found");
			}
			if (event.getCustomerId() != customer.getId()) {
				throw new Exception("This event does not belong to the customer");
			}

			HasExamStartedResponse response = new HasExamStartedResponse();
			response.setValuesFromExamTaken(examTaken);

			responseBody.add("response", GSON.toJsonTree(response));

		} catch (Throwable e) {
			logger.log(e.getMessage());
			responseBody.addProperty("Exception", e.getMessage());
			responseBody.addProperty("StackTrace", WardenioUtils.getStackTrace(e));
			responseBody.addProperty("body", "{}");
		} finally {
			String responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
			writer.write(responseStr);
			writer.close();
		}
	}
}

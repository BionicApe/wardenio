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
import com.konado.wardenio.model.Customer;
import com.konado.wardenio.requests.CreateEventRequest;
import com.konado.wardenio.responses.CreateEventResponse;

public class CreateEventFunction implements RequestStreamHandler {

	private static CustomerDao customerDao = new CustomerDao();
	private static EventDao eventDao = new EventDao();
	private static final Gson gson = new Gson();

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject responseBody = new JsonObject();

//		String inputStr = reader.lines().collect(Collectors.joining("\n"));
//		logger.log(inputStr);
//		
//		responseBody.addProperty("InputStream",inputStr);		
		String responseStr = "Failure";
		try {

			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);
//			responseBody.addProperty("jsonRequest",jsonRequest.toString());			

			CreateEventRequest request = gson.fromJson((String) jsonRequest.get("body").getAsString(), CreateEventRequest.class);

			Customer customer = customerDao.GetByUsernameAndPassword(request.getCustomerUsername(), request.getCustomerPassword());

			if (customer == null) {
				throw new Exception("Customer not found");
			} else {
				Long eventId = eventDao.save(request, customer);
				if (eventId > 0) {
					CreateEventResponse response = new CreateEventResponse();
					response.setEventId(eventId);
					response.setEventData(request.getEventData());
//					responseBody.addProperty("response", gson.toJson(response));
					responseBody.add("response", gson.toJsonTree(response));
				} else {
					throw new Exception("event not created");
				}
			}

			responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
		} catch (Exception e) {
			logger.log(e.getMessage());
			responseBody.addProperty("Exception", e.getMessage());
			responseBody.addProperty("StackTrace", WardenioUtils.getStackTrace(e));
		}

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseStr);
		writer.close();
	}
}

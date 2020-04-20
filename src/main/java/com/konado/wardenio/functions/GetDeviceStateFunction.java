package com.konado.wardenio.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.konado.wardenio.WardenioUtils;
import com.konado.wardenio.dao.CustomerDao;
import com.konado.wardenio.dao.DeviceStateDao;
import com.konado.wardenio.dao.EventDao;
import com.konado.wardenio.dao.ExamTakenDao;
import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.DeviceState;
import com.konado.wardenio.model.Event;
import com.konado.wardenio.model.ExamTaken;
import com.konado.wardenio.requests.GetDeviceStateRequest;
import com.konado.wardenio.responses.GetDeviceStateResponse;

public class GetDeviceStateFunction implements RequestStreamHandler {

	private static CustomerDao customerDao = new CustomerDao();
	private static ExamTakenDao examTakenDao = new ExamTakenDao();
	private static EventDao eventDao = new EventDao();
	private static DeviceStateDao stateDeviceDao = new DeviceStateDao();
	private static final Gson GSON = WardenioUtils.GSON;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject responseBody = new JsonObject();

		try {

			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);

			GetDeviceStateRequest request = GSON.fromJson((String) jsonRequest.get("body").getAsString(), GetDeviceStateRequest.class);

			Customer customer = customerDao.GetByUsernameAndPassword(request.getCustomerUsername(), request.getCustomerPassword());
			if (customer == null) {
				throw new Exception("Customer not found");
			}
			ExamTaken examTaken = examTakenDao.get(request.getExamTakenId());
			if (examTaken == null) {
				throw new Exception("Exam Taken not found: " +
						request.getExamTakenId());
			}
			Event event = eventDao.get(examTaken.getId());
			if (event == null) {
				throw new Exception("Event not found");
			}
			if (event.getCustomerId() != customer.getId()) {
				throw new Exception("This event does not belong to the customer");
			}

			List<DeviceState> resultBeans = stateDeviceDao.getAllByExamTakenId(examTaken.getId());

			GetDeviceStateResponse response = new GetDeviceStateResponse();
			response.setDeviceStateList(resultBeans);
			response.setTimestamp(resultBeans.get(0).getTimestamp());

			responseBody.add("response", GSON.toJsonTree(response));

		} catch (Exception e) {
			logger.log(e.getMessage());
			responseBody.addProperty("Exception", e.getMessage());
			responseBody.addProperty("StackTrace", WardenioUtils.getStackTrace(e));
		}

		String responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseStr);
		writer.close();
	}

}

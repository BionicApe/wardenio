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
import com.konado.wardenio.KinesisUitils;
import com.konado.wardenio.S3Utils;
import com.konado.wardenio.WardenioUtils;
import com.konado.wardenio.dao.CustomerDao;
import com.konado.wardenio.dao.EventDao;
import com.konado.wardenio.dao.ExamTakenDao;
import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.Event;
import com.konado.wardenio.model.ExamTaken;
import com.konado.wardenio.requests.StartExamRequest;
import com.konado.wardenio.responses.StartExamResponse;

public class StartExamFunction implements RequestStreamHandler {

	private static ExamTakenDao examTakenDao = new ExamTakenDao();
	private static final Gson gson = new Gson();
	private static CustomerDao customerDao = new CustomerDao();
	private static EventDao eventDao = new EventDao();

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject responseBody = new JsonObject();
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");

		try {

			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);

			StartExamRequest request = new StartExamRequest();
			request.setPin(jsonRequest.get("body").getAsString().toUpperCase());

			ExamTaken examTaken = examTakenDao.getByPin(request.getPin());

			if (examTaken == null) {
				throw new Exception("PinNotFound");
			}
			StartExamResponse response = new StartExamResponse();
			if (!examTaken.hasStarted()) {

				Event event = eventDao.get(examTaken.getEventId());
				if (event == null) {
					throw new Exception("Event not found");
				}
				Customer customer = customerDao.get(event.getCustomerId());
				S3Utils.createAndSetBucket(customer, examTaken);
				KinesisUitils.createAndSetStreamName(customer,examTaken);
		
				examTakenDao.updateStartExam(examTaken);				
			}
			response.setStudentName(examTaken.getStudentName());
			response.setBucketName(examTaken.getBucketName());
			response.setStreamName(examTaken.getStreamName());
			responseBody.add("response", gson.toJsonTree(response));

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

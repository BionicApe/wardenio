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
import com.konado.wardenio.dao.DeviceStateDao;
import com.konado.wardenio.dao.ExamTakenDao;
import com.konado.wardenio.model.ExamTaken;
import com.konado.wardenio.requests.SetDeviceStateRequest;
import com.konado.wardenio.responses.SetDeviceStateResponse;

public class SetDeviceStateFunction implements RequestStreamHandler {
	private static ExamTakenDao examTakenDao = new ExamTakenDao();
	private static DeviceStateDao deviceStateDao = new DeviceStateDao();
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
			SetDeviceStateRequest request = GSON.fromJson((String) jsonRequest.get("body").getAsString(), SetDeviceStateRequest.class);
			request.validate();

			ExamTaken examTaken = examTakenDao.get(request.getExamTakenId());

			if (examTaken == null) {
				throw new Exception("ExamTaken not found:" +
						request.getExamTakenId());
			}

			Long deviceStateId = deviceStateDao.createNew(examTaken, request);

			SetDeviceStateResponse response = new SetDeviceStateResponse(deviceStateId);

			responseBody.add("response", GSON.toJsonTree(response));

		} catch (Throwable e) {
			logger.log(e.getMessage());
			responseStr = WardenioUtils.createUnsuccessfulResponse(responseBody, e);
		} finally {
			responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
			writer.write(responseStr);
			writer.close();
		}
	}
}

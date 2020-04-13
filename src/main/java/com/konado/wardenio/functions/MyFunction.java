//package com.konado.wardenio.functions;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.util.List;
//
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.LambdaLogger;
//import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.Bucket;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.konado.wardenio.WardenioSecurity;
//import com.konado.wardenio.WardenioUtils;
//import com.konado.wardenio.requests.StartExamRequest;
//import com.konado.wardenio.responses.StartExamResponse;
//
//public class MyFunction implements RequestStreamHandler {
//	
//	private static final Gson gson = new Gson();
//
//	@Override
//	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
//
//		LambdaLogger logger = context.getLogger();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//		JsonObject responseBody = new JsonObject();
//		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
//
//		try {
//
//			JsonObject jsonRequest = (JsonObject) JsonParser.parseReader(reader);
//
//			StartExamRequest request = new StartExamRequest();
//			request.setPin(jsonRequest.get("body").getAsString().toUpperCase());
//
//			StartExamResponse response = new StartExamResponse();
//
//			AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
//			List<Bucket> buckets = s3client.listBuckets();
////			WardenioSecurity.generateSessionCredentials(request.getPin(), response);
//			WardenioSecurity.generateSessionCredentials2(request.getPin());
//
//			responseBody.add("response", gson.toJsonTree(response));
//
//		} catch (Throwable e) {
//			logger.log(e.getMessage());
//			responseBody.addProperty("Exception", e.getMessage());
//			responseBody.addProperty("StackTrace", WardenioUtils.getStackTrace(e));
//			responseBody.addProperty("body", "{}");
//		} finally {
//			String responseStr = WardenioUtils.createSuccessfulResponse(responseBody);
//			writer.write(responseStr);
//			writer.close();
//		}
//	}
//}

package com.konado.wardenio;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.konado.wardenio.model.Event;

public class WardenioUtils {

	public static Event createEventFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Event.class);
	}

	public static String createSuccessfulResponse(JsonObject responseBody) {
		JsonObject responseJson = new JsonObject();
		JsonObject headerJson = new JsonObject();
		headerJson.addProperty("x-Wardenio-header", "Success");

		responseJson.addProperty("statusCode", 200);
		responseJson.add("headers", headerJson);
		responseJson.addProperty("body", responseBody.toString());

//		JsonObject responseJson = new JsonObject();
//		JsonObject headerJson = new JsonObject();
//		headerJson.addProperty("x-Wardenio-header", "Success");
//
//		responseJson.addProperty("statusCode", 400);
//		responseJson.add("headers", headerJson);
//		responseJson.add("body", responseBody);
//
//		return responseJson.getAsString();

		return responseJson.toString();
	}

	public static void createUnsuccessfulResponse(JsonObject responseJson) {
		JsonObject responseBody = new JsonObject();
		responseBody.addProperty("message", "Unsuccess");

		JsonObject headerJson = new JsonObject();
		headerJson.addProperty("x-Wardenio-header", "Unsuccess");

		responseJson.addProperty("statusCode", 400);
		responseJson.add("headers", headerJson);
		responseJson.addProperty("body", responseBody.toString());
	}

	public static void debugEventContext(Map<String, String> event, Context context) {
		LambdaLogger logger = context.getLogger();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// log execution details
		logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
		logger.log("CONTEXT: " + gson.toJson(context));
		// process event
		logger.log("EVENT: " + gson.toJson(event));
		logger.log("EVENT TYPE: " + event.getClass().toString());
	}

	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	public static String readSystemEnvironment()
	{
	    StringBuilder sb = new StringBuilder();
	    String logFormat = "%s=%s\n";
	    sb.append("==================\n");
	    sb.append("Environment variables:\n");
	    sb.append("======================\n");
	    Map<String, String> map = System.getenv();
	    SortedSet<String> keys = new TreeSet<>(map.keySet());
	    for (String key : keys) {
	        sb.append(String.format(logFormat, key, map.get(key)));
	    }
	    sb.append("==================\n");
	    sb.append("System properties:\n");
	    sb.append("==================\n");
	    // Get all system properties
	    Properties props = System.getProperties();
	    SortedSet<String> propNames = new TreeSet<>(props.stringPropertyNames());
	    for (String propName : propNames) {
	        // Get property value
	        String propValue = (String) props.get(propName);
	        sb.append(String.format(logFormat, propName, propValue));
	    }
	    return sb.toString();
	}
	
}

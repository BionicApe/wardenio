package com.konado.wardenio;

import static com.amazonaws.SDKGlobalConfiguration.ACCESS_KEY_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.ALTERNATE_ACCESS_KEY_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.ALTERNATE_SECRET_KEY_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.AWS_SESSION_TOKEN_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.SECRET_KEY_ENV_VAR;

public class WardenioDebugHelper {

	public static String getAccessVariables() {
		StringBuilder sb = new StringBuilder();
		sb.append("ACCESS_KEY_ENV_VAR:");
		sb.append(System.getenv(ACCESS_KEY_ENV_VAR));
		sb.append("||");

		sb.append("ALTERNATE_ACCESS_KEY_ENV_VAR:");
		sb.append(System.getenv(ALTERNATE_ACCESS_KEY_ENV_VAR));
		sb.append("||");

		sb.append("SECRET_KEY_ENV_VAR:");
		sb.append(System.getenv(SECRET_KEY_ENV_VAR));
		sb.append("||");

		sb.append("ALTERNATE_SECRET_KEY_ENV_VAR:");
		sb.append(System.getenv(ALTERNATE_SECRET_KEY_ENV_VAR));
		sb.append("||");

		sb.append("AWS_SESSION_TOKEN_ENV_VAR:");
		sb.append(System.getenv(AWS_SESSION_TOKEN_ENV_VAR));
		sb.append("||");

		return sb.toString();
	}
}

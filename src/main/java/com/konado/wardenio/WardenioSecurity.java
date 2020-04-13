package com.konado.wardenio;

import java.io.IOException;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetFederationTokenRequest;
import com.amazonaws.services.securitytoken.model.GetFederationTokenResult;
import com.konado.wardenio.responses.StartExamResponse;

public class WardenioSecurity {

	public static final int TOKEN_DURATION = 129500; // ~36 HOURS
//	public static final int TOKEN_DURATION = 600; // ~10 MINUTES

	public static final Regions CLIENT_REGION = Regions.EU_CENTRAL_1;
	public static final String BUCKET_NAME = "wardenio-security-test";
	public static final String FEDERATED_USER = "*** Federated user name ***";
	public static final String RESOURCE_ARN = "arn:aws:s3:::" + BUCKET_NAME;

	public static void main(String[] args) throws IOException {
		try {
			generateSessionCredentials2("adsfadfa");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateSessionCredentials2(String accessPin) throws Exception {

		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(CLIENT_REGION).build();

		GetFederationTokenRequest getFederationTokenRequest = new GetFederationTokenRequest();
		getFederationTokenRequest.setDurationSeconds(7200);
		getFederationTokenRequest.setName(accessPin);

		// Define the policy and add it to the request.
		Policy policy = new Policy();
		policy.withStatements(new Statement(Effect.Allow).withActions(S3Actions.ListObjects).withResources(new Resource(RESOURCE_ARN)));
		getFederationTokenRequest.setPolicy(policy.toJson());

		// Get the temporary security credentials.
		GetFederationTokenResult federationTokenResult = stsClient.getFederationToken(getFederationTokenRequest);
		Credentials sessionCredentials = federationTokenResult.getCredentials();

		// Package the session credentials as a BasicSessionCredentials
		// object for an Amazon S3 client object to use.
		BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(), sessionCredentials.getSessionToken());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials)).withRegion(CLIENT_REGION).build();

		// To verify that the client works, send a listObjects request using
		// the temporary security credentials.
		ObjectListing objects = s3Client.listObjects(BUCKET_NAME);
		System.out.println("No. of Objects = " + objects.getObjectSummaries().size());
	}

	public static void generateSessionCredentials(String accessPin, StartExamResponse response) throws Exception {

		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(new EnvironmentVariableCredentialsProvider()).withRegion(CLIENT_REGION).build();

		GetFederationTokenRequest getFederationTokenRequest = new GetFederationTokenRequest();
		getFederationTokenRequest.setDurationSeconds(WardenioSecurity.TOKEN_DURATION);
		getFederationTokenRequest.setName(accessPin);

		// Define the policy and add it to the request.
		Policy policy = new Policy();
		policy.withStatements(new Statement(Effect.Allow).withActions(S3Actions.ListObjects).withResources(new Resource(RESOURCE_ARN)));
		getFederationTokenRequest.setPolicy(policy.toJson());

//		policy.withStatements(new Statement(Effect.Allow).withActions(S3Actions.ListObjects, S3Actions.GetObject, S3Actions.PutObject).withResources(new Resource(resourceARN)));
//		getFederationTokenRequest.setPolicy(policy.toJson());

		// Get the temporary security credentials.
		GetFederationTokenResult federationTokenResult = stsClient.getFederationToken(getFederationTokenRequest);
		Credentials sessionCredentials = federationTokenResult.getCredentials();

		response.setSessionAccessKeyId(sessionCredentials.getAccessKeyId());
		response.setSecretAccessKey(sessionCredentials.getSecretAccessKey());
		response.setSessionToken(sessionCredentials.getSessionToken());
//
//		// Package the session credentials as a BasicSessionCredentials
//		// object for an Amazon S3 client object to use.
//		BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(), sessionCredentials.getSessionToken());
//		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials)).withRegion(clientRegion).build();
//
//		// To verify that the client works, send a listObjects request using
//		// the temporary security credentials.
//		ObjectListing objects = s3Client.listObjects(bucketName);
//
//		return "No. of Objects = " + objects.getObjectSummaries().size();

	}

}
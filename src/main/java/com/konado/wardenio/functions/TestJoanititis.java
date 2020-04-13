package com.konado.wardenio.functions;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class TestJoanititis implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		
		StringBuilder sb = new StringBuilder();
		try {
			context.getLogger().log("Input: " + input);
			
			AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
			List<Bucket> buckets = s3client.listBuckets();

			for (Bucket bucket : buckets) {
				sb.append(bucket.getName());
				sb.append("\n");
			}
			
			
		} catch (Exception e) {
			context.getLogger().log(e.getMessage());
		}
		return "Hello from Lambda!\n" + sb.toString();
	}

}

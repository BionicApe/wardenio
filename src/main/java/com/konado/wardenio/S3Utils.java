package com.konado.wardenio;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.ExamTaken;

public class S3Utils {

	private static final Regions REGION = Regions.EU_CENTRAL_1;

	public static String listAllBuckets(Customer customer, ExamTaken examTaken) throws Exception {

//		AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient()
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION).build();
		List<Bucket> buckets = s3Client.listBuckets();

		StringBuilder sb = new StringBuilder();
		for (Bucket bucket : buckets) {
			sb.append(bucket.getName());
			sb.append("||");
		}

		return sb.toString();
	}

	public static void createAndSetBucket(Customer customer, ExamTaken examTaken) throws Exception {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION).build();

		{
			String bucketName = generateAndTestName(customer.getBucketName(), examTaken, s3Client);

			// Because the CreateBucketRequest object doesn't specify a region, the
			// bucket is created in the region specified in the client.
			s3Client.createBucket(new CreateBucketRequest(bucketName));
			examTaken.setBucketName(bucketName);
		}
		{
			String desktopBucketName = generateAndTestName(customer.getDesktopBucketName(), examTaken, s3Client);

			// Because the CreateBucketRequest object doesn't specify a region, the
			// bucket is created in the region specified in the client.
			s3Client.createBucket(new CreateBucketRequest(desktopBucketName));
			examTaken.setDesktopBucketName(desktopBucketName);
		}
	}

	private static String generateAndTestName(String bucketPrefix, ExamTaken examTaken, AmazonS3 s3Client) {
		StringBuilder sb = new StringBuilder();
		sb.append(bucketPrefix);
		sb.append("-exam-");
		sb.append(examTaken.getEventId());
		sb.append("-");
		sb.append(examTaken.getId());
		sb.append("-");
		sb.append(WardenioStringTools.generateRandomCode(2));
		String bucketName = sb.toString();// S3 only allows Lower Case

		if (!s3Client.doesBucketExistV2(bucketName)) {
			return bucketName;
		}

		//we call it recursively to try to get a different sufix so the bucket name is unique  
		return generateAndTestName(bucketPrefix, examTaken, s3Client);
	}

	public static void putObject(Customer customer, ExamTaken examTaken) throws Exception {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION).build();

		StringBuilder sbObjectName = new StringBuilder();
		sbObjectName.append("exam-");
		sbObjectName.append(examTaken.getEventId());
		sbObjectName.append("-");
		sbObjectName.append(examTaken.getId());

		s3Client.putObject(customer.getBucketName(), sbObjectName.toString(), "This is a great test!");
	}
}

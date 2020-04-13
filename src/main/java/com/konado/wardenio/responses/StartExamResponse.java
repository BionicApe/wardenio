package com.konado.wardenio.responses;

public class StartExamResponse {

	public class VideoSettings {

		private int width = 1280;
		private int height = 720;
		private int bitrate = 2000;
		private int framerate = 30;
		private int lifespanHours = 8760;

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getBitrate() {
			return bitrate;
		}

		public void setBitrate(int bitrate) {
			this.bitrate = bitrate;
		}

		public int getFramerate() {
			return framerate;
		}

		public void setFramerate(int framerate) {
			this.framerate = framerate;
		}

		public int getLifespanHours() {
			return lifespanHours;
		}

		public void setLifespanHours(int lifespanHours) {
			this.lifespanHours = lifespanHours;
		}

	}

	private String studentName;

	private String bucketName;

	private String streamName;

	private String accessKeyId;

	private String secretAccessKey;

	private String sessionToken;

	private VideoSettings videoSettings = new VideoSettings();

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public void setSessionAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public VideoSettings getVideoSettings() {
		return videoSettings;
	}

	public void setVideoSettings(VideoSettings videoSettings) {
		this.videoSettings = videoSettings;
	}
}

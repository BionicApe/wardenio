package com.konado.wardenio.model;

public class Customer {

	protected Long id;

	private String password;

	private String username;

	private String bucketName;

	private String streamName;

	private String desktopStreamName;

	private String desktopBucketName;

	public Customer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getDesktopStreamName() {
		return desktopStreamName;
	}

	public void setDesktopStreamName(String desktopStreamName) {
		this.desktopStreamName = desktopStreamName;
	}

	public String getDesktopBucketName() {
		return desktopBucketName;
	}

	public void setDesktopBucketName(String desktopBucketName) {
		this.desktopBucketName = desktopBucketName;
	}

}

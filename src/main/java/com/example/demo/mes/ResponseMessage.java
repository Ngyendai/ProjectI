package com.example.demo.mes;

public class ResponseMessage {
	private String message;
	private String fileDownloadUri;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseMessage() {
		
	}
	public ResponseMessage(String message, String fileDownloadUri) {
		super();
		this.message = message;
		this.fileDownloadUri = fileDownloadUri;
	}
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
}

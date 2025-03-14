package com.hanumant.model;


public class SmsResponse {
    private String status;
    private String responseCode;
    private String acknowledgementId;
    
    
    
	public SmsResponse(String status, String responseCode, String acknowledgementId) {
		this.status = status;
		this.responseCode = responseCode;
		this.acknowledgementId = acknowledgementId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getAcknowledgementId() {
		return acknowledgementId;
	}
	public void setAcknowledgementId(String acknowledgementId) {
		this.acknowledgementId = acknowledgementId;
	}
    
    
}

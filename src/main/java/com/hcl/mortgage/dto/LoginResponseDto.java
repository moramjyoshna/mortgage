package com.hcl.mortgage.dto;

import java.io.Serializable;

public class LoginResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private Integer statusCode;
	private String message;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LoginResponseDto() {
		super();
	}
	
}

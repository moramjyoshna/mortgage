package com.hcl.mortgage.dto;

import java.io.Serializable;

public class AccountSummaryResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long accountNumber;
	private Double accountBalance;
	private String customerName;
	private Integer statusCode;
	private String message;
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public AccountSummaryResponseDto() {
		super();
	}
	
}

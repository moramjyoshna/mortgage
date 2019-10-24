package com.hcl.mortgage.dto;

import java.io.Serializable;

public class LoanRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private Double emi;
	private Double loanAmount;
	private Float loanTenure;
	private String propertyType;
	private Double propertyValue;
	private Double totalAmount;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Double getEmi() {
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Float getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(Float loanTenure) {
		this.loanTenure = loanTenure;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Double propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
}

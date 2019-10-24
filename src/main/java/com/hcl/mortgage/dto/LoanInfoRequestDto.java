package com.hcl.mortgage.dto;

import java.io.Serializable;

public class LoanInfoRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer customerId;
	private Double propertyValue;
	private String propertyType;
	private Double annualSalary;
	private Float loanTenure;
	private Double loanAmount;
	
	public Double getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(Double propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public Double getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(Double annualSalary) {
		this.annualSalary = annualSalary;
	}
	public Float getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(Float loanTenure) {
		this.loanTenure = loanTenure;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public LoanInfoRequestDto() {
		super();
	}
	
}

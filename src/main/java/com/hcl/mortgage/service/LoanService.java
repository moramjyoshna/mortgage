package com.hcl.mortgage.service;

import com.hcl.mortgage.dto.LoanInfoRequestDto;
import com.hcl.mortgage.dto.LoanInfoResponseDto;
import com.hcl.mortgage.dto.LoanRequestDto;
import com.hcl.mortgage.entity.LoanDetails;

public interface LoanService {

	LoanInfoResponseDto loanInfo(LoanInfoRequestDto loanInforequestDto);
	LoanDetails getLoanSummary(Integer customerId);
	String applyLoan(LoanRequestDto loanRequestDto);
}

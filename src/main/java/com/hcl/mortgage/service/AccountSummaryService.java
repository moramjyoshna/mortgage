package com.hcl.mortgage.service;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;

public interface AccountSummaryService {

	AccountSummaryResponseDto getAccountSummary(Integer customerId);
}

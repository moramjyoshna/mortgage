package com.hcl.mortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.service.AccountSummaryService;

/**
 * @author Jyoshna
 *
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")

public class AccountSummaryController {

	private static final Logger logger = LoggerFactory.getLogger(AccountSummaryController.class);

	@Autowired
	AccountSummaryService accountSummaryService;

	/*
	 * This method is used to get the account details for particular customer.
	 * 
	 * @Param customerId
	 * 
	 * @return AccountSummaryResponseDto is the return object which includes
	 * accountNumber,accountBalance,customerName,message,statusCode
	 * 
	 */

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<AccountSummaryResponseDto> getAccountSummary(@PathVariable Integer customerId) {
		logger.info("inside account summary controller");
		return new ResponseEntity<>(accountSummaryService.getAccountSummary(customerId), HttpStatus.OK);
	}
}

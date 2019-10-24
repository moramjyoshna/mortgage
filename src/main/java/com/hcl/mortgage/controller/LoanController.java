package com.hcl.mortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mortgage.dto.LoanInfoRequestDto;
import com.hcl.mortgage.dto.LoanInfoResponseDto;
import com.hcl.mortgage.dto.LoanRequestDto;
import com.hcl.mortgage.entity.LoanDetails;
import com.hcl.mortgage.exception.Response;
import com.hcl.mortgage.service.LoanService;

/**
 * @author Jyoshna, Subashri
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoanController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	LoanService loanServcie;

	/*
	 * This method is used for loan enquiry(emi, totalloanAmount, rateOfInterest) if
	 * they are ok with details of loan they they will apply loan.
	 * 
	 * @Param LoanInfoRequestDto object which includes customerId,
	 * propertyValue,propertyType,loanTenure,loanAmount,annualSalary
	 * 
	 * @return LoanInfoResponseDto is the return object which includes
	 * rateOfInterest,totalAmount,emi message,statusCode
	 * 
	 */

	@PostMapping("/loanInfo")
	public ResponseEntity<LoanInfoResponseDto> info(@RequestBody LoanInfoRequestDto loanInfoRequestDto) {
		logger.info("inside loan enquiry controller");
		LoanInfoResponseDto loanInfoResponseDto = loanServcie.loanInfo(loanInfoRequestDto);
		return new ResponseEntity<>(loanInfoResponseDto, HttpStatus.CREATED);
	}

	/*
	 * This method is used to apply loan
	 * 
	 * @Param LoanResponse object which includes customerId,
	 * propertyValue,propertyType,loanTenure,loanAmount,annualSalary,totalAmount
	 * 
	 * @return ResponseDto is the return object which includes message,statusCode
	 * 
	 */

	@PostMapping("/loan")
	public ResponseEntity<Response> applyLoan(@RequestBody LoanRequestDto loanRequestDto) {
		logger.info("inside loan apply controller");
		Response loanResponse = new Response();
		String message = loanServcie.applyLoan(loanRequestDto);
		loanResponse.setMessage(message);
		loanResponse.setStatusCode(201);
		return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
	}

	/*
	 * This method is used to get the applied loan summary
	 * 
	 * 
	 * @Param customerId
	 * 
	 * 
	 * @return LoanResponseDto is the return object which includes
	 * loanTenure,loanAmount,loanAccountNumber rateOfInterest,totalAmount,emi
	 * message,statusCode
	 * 
	 */

	@GetMapping("/loans/{customerId}/summary")
	public ResponseEntity<LoanDetails> loanSummary(@PathVariable Integer customerId) {
		logger.info("inside loan summary controller");
		LoanDetails loanResponse = new LoanDetails();
		LoanDetails loan = loanServcie.getLoanSummary(customerId);
		loanResponse.setLoanAccountNumber(loan.getLoanAccountNumber());
		loanResponse.setLoanAmount(loan.getLoanAmount());
		loanResponse.setEmi(loan.getEmi());
		loanResponse.setLoanTenure(loan.getLoanTenure());
		loanResponse.setRateOfInterest(loan.getRateOfInterest());
		loanResponse.setTotalAmount(loan.getTotalAmount());
		return new ResponseEntity<>(loanResponse, HttpStatus.OK);
	}

}

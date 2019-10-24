package com.hcl.mortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mortgage.dto.CustomerRequestDto;
import com.hcl.mortgage.exception.Response;
import com.hcl.mortgage.service.CustomerService;

/**
 * @author Subashri Sridharan
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })

public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	/*
	 * This method is used for customer registration.
	 * 
	 * @Param CustomerRequestDto object which includes
	 * customerName,emailId,mobileNumber,dateOfBirth,automatic generation of
	 * accountNumber and password
	 * 
	 * @return ResponseDto is the return object which includes
	 * message,statusCode
	 * 
	 */

	@PostMapping("/register")
	public ResponseEntity<Response> customerRegistration(
			@RequestBody CustomerRequestDto customerRequestDto) {
		logger.info("inside customer controller");
		Response customerResponseDto = new Response();
		String message = customerService.registerCustomer(customerRequestDto);
		customerResponseDto.setMessage(message);
		customerResponseDto.setStatusCode(200);

		return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
	}
}

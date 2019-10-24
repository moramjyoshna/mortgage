package com.hcl.mortgage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.LoginRequestDto;
import com.hcl.mortgage.dto.LoginResponseDto;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.ExceptionConstants;
import com.hcl.mortgage.util.PasswordUtil;
/**
 * @author Jyoshna
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordUtil passwordUtil;

	/*
	 * This method is used to login the customer by providing valid credentials
	 * 
	 * @Param emailId,password
	 * 
	 * @return LoginResponseDto is the return object which includes
	 * customerId,message,statusCode
	 * 
	 */

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {

		logger.info("inside login service");
		Customer customer = customerRepository.findByEmailIdAndPassword(loginRequestDto.getEmailId(),
				loginRequestDto.getPassword());
		if (customer == null) {
			throw new CommonException(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}

		customer.setEmailId(loginRequestDto.getEmailId());
		customer.setPassword(loginRequestDto.getPassword());

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setCustomerId(customer.getCustomerId());
		loginResponseDto.setStatusCode(201);
		loginResponseDto.setMessage("Login is successfull...");
		return loginResponseDto;
	}

}

package com.hcl.mortgage.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.CustomerRequestDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.Email;
import com.hcl.mortgage.util.ExceptionConstants;
import com.hcl.mortgage.util.Sms;

/**
 * @author Subashri Sridharan
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	JavaMailSender javaMailSender;
	Sms sms = new Sms();
	Email email = new Email();

	@Override

	/*
	 * This method is used for customer registration.
	 * 
	 * @Param CustomerRequestDto object which includes
	 * customerName,emailId,mobileNumber,dateOfBirth,automatic generation of
	 * accountNumber and password
	 * 
	 * @return CustomerResponseDto is the return object which includes
	 * message,statusCode Age , Mobile ,Email Validation
	 */

	public String registerCustomer(CustomerRequestDto customerRequestDto) {
		logger.info("register Customer service");
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerRequestDto, customer);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate date = LocalDate.parse(customerRequestDto.getDob(), formatter);
		Period period = Period.between(date, LocalDate.now());
		if (period.getYears() < 18) {
			throw new CommonException(ExceptionConstants.AGE_INVALID);
		}
		if (!customerRequestDto.getEmailId().matches(regex)) {
			throw new CommonException(ExceptionConstants.EMAIL_INVALID);
		}
		if (Long.toString(customerRequestDto.getMobileNumber()).length() < 10) {
			throw new CommonException(ExceptionConstants.MOBILE_INVALID);
		}

		String randomPassword = RandomStringUtils.randomAlphanumeric(10);
		Account account = new Account();
		long accountNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		customer.setPassword(randomPassword);
		customer.setDateOfBirth(date);
		customerRepository.save(customer);
		account.setAccountNumber(accountNumber);
		account.setAccountBalance(100000.0);
		account.setCustomerId(customer.getCustomerId());
		accountRepository.save(account);
		sms.sendSms(customerRequestDto.getMobileNumber(), accountNumber, randomPassword, "Salary");
		email.sendEmail(customerRequestDto.getEmailId(), accountNumber, randomPassword, javaMailSender, "Salary");
		return "Registered Successfully";
	}

}

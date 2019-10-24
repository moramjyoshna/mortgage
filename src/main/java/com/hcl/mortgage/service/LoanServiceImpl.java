package com.hcl.mortgage.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.LoanInfoRequestDto;
import com.hcl.mortgage.dto.LoanInfoResponseDto;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.ExceptionConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.javamail.JavaMailSender;

import com.hcl.mortgage.dto.LoanRequestDto;
import com.hcl.mortgage.entity.LoanDetails;
import com.hcl.mortgage.repository.LoanRepository;
import com.hcl.mortgage.util.Email;
import com.hcl.mortgage.util.Sms;

/**
 * @author Jyoshna, Subashri
 *
 */

@Service
public class LoanServiceImpl implements LoanService {

	private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	JavaMailSender javaMailSender;
	Sms sms = new Sms();
	Email email = new Email();

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

	@Override

	public LoanInfoResponseDto loanInfo(LoanInfoRequestDto loanInforequestDto) {
		logger.info("inside loan enquiry service");
		Customer customers = null;
		Optional<Customer> customer = customerRepository.findByCustomerId(loanInforequestDto.getCustomerId());
		Double loanAmount = loanInforequestDto.getLoanAmount();
		Double propertyValue = loanInforequestDto.getPropertyValue();
		Double annualSalary = loanInforequestDto.getAnnualSalary();
		Float rateOfInterest = 0.04F;
		Float loanTenure = loanInforequestDto.getLoanTenure();
		Float months = loanTenure * 12;
		Double eligibleAmount = 0.0;
		Double emi = 0.0;
		Double totalAmount = 0.0;

		if (!customer.isPresent()) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		}

		customers = customer.get();

		Period period = Period.between(customers.getDateOfBirth(), LocalDate.now());

		if (loanAmount >= propertyValue) {
			throw new CommonException(ExceptionConstants.LOANNOT_APPLICABLE);
		}

		if (period.getYears() >= 18 && period.getYears() < 75) {
			if (annualSalary >= 240000 && annualSalary <= 600000) {
				eligibleAmount = propertyValue * 0.7;
				totalAmount = Math.ceil(loanAmount + (loanAmount * rateOfInterest));
				emi = Math.ceil(totalAmount / months);
				if (emi >= (0.6 * (annualSalary / 12))) {

					throw new CommonException(ExceptionConstants.LOAN_NOT_ELIGIBLE);
				}
			}
			if (annualSalary >= 600001) {
				eligibleAmount = propertyValue * 0.8;
				totalAmount = Math.ceil(loanAmount + (loanAmount * rateOfInterest));
				emi = Math.ceil(totalAmount / months);
				if (emi >= (0.6 * (annualSalary / 12))) {

					throw new CommonException(ExceptionConstants.LOAN_NOT_ELIGIBLE);
				}
			}
		} else {
			throw new CommonException(ExceptionConstants.LOAN_INVALID);
		}

		if (loanAmount >eligibleAmount) {
			throw new CommonException(
					ExceptionConstants.LOANNOT_APPLICABLE + ". your maximum eligibility is " + eligibleAmount);
		}

		LoanInfoResponseDto loanInfoResponseDto = new LoanInfoResponseDto();
		loanInfoResponseDto.setTotalAmount(totalAmount);
		loanInfoResponseDto.setLoanAmount(loanAmount);
		loanInfoResponseDto.setEmi(emi);
		loanInfoResponseDto.setRateOfInterest(4F);
		loanInfoResponseDto.setStatusCode(201);
		loanInfoResponseDto.setMessage("Enquiry Successfull");
		return loanInfoResponseDto;
	}

	/*
	 * This method is used to apply loan
	 * 
	 * @Param LoanResponse object which includes customerId,
	 * propertyValue,propertyType,loanTenure,loanAmount,annualSalary,totalAmount
	 * 
	 * @return LoanResponse is the return object which includes message,statusCode
	 * 
	 */

	public String applyLoan(LoanRequestDto loanRequestDto) {
		logger.info("inside loan apply service");
		LoanDetails loanDetail = new LoanDetails();
		BeanUtils.copyProperties(loanRequestDto, loanDetail);
		Customer customer = null;
		Optional<Customer> customerInfo = customerRepository.findByCustomerId(loanRequestDto.getCustomerId());
		if (!customerInfo.isPresent()) {
			throw new CommonException(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}
		customer = customerInfo.get();
		LoanDetails loanData = loanRepository.findByCustomerId(customer.getCustomerId());
		if (loanData != null) {
			throw new CommonException(ExceptionConstants.LOAN_NOT_APPLICABLE);
		}
		long accountNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		loanDetail.setLoanAccountNumber(accountNumber);
		loanDetail.setRateOfInterest(4F);
		loanDetail.setEmi(loanRequestDto.getEmi());
		loanDetail.setTotalAmount(loanRequestDto.getTotalAmount());
		loanDetail.setEmiDate(LocalDate.now());
		loanDetail.setOutStandingBalance(loanRequestDto.getTotalAmount());
		sms.sendSms(customer.getMobileNumber(), accountNumber, customer.getPassword(), "Loan");
		email.sendEmail(customer.getEmailId(), accountNumber, customer.getPassword(), javaMailSender, "Loan");
		loanRepository.save(loanDetail);
		return "Loan Applied Successfully";
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

	@Override
	public LoanDetails getLoanSummary(Integer customerId) {
		logger.info("inside loan summary service");
		LoanDetails loanInfo;
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent()) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		} else {
			loanInfo = loanRepository.findByCustomerId(customerId);
		}
		if (loanInfo == null) {
			throw new CommonException(ExceptionConstants.LOANINFO_UNAVAILABLE);
		}
		return loanInfo;
	}

}

package com.hcl.mortgage.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.javamail.JavaMailSender;

import com.hcl.mortgage.dto.CustomerRequestDto;
import com.hcl.mortgage.dto.LoanInfoRequestDto;
import com.hcl.mortgage.dto.LoanInfoResponseDto;
import com.hcl.mortgage.dto.LoanRequestDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.entity.LoanDetails;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.repository.LoanRepository;
import com.hcl.mortgage.util.Email;
import com.hcl.mortgage.util.Sms;

/**
 * @author Subashri, Jyoshna
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(LoanServiceTest.class);

	@Mock
	LoanRepository loanRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	LoanServiceImpl loanServiceImpl;

	Customer customer;

	Optional<Customer> customers;

	LoanInfoRequestDto loanInfoRequestDto;

	LoanInfoResponseDto loanInfoResponseDto;

	LoanDetails loanDetail;

	@Mock
	JavaMailSender javaMailSender;

	String message = null;

	CustomerRequestDto customerRequestDto;

	LoanRequestDto loanRequestDto;

	Account account;

	Sms sms = new Sms();

	Email email = new Email();

	@Before
	public void setup() {

		customer = new Customer();
		customer.setCustomerId(1);
		customer.setPassword("@^+=&");
		customer.setCustomerName("Jyoshna");
		customer.setEmailId("jyoshna@gmail.com");
		customer.setMobileNumber(9566838401L);
		LocalDate date = LocalDate.parse("1996-04-12");
		customer.setDateOfBirth(date);

		customerRequestDto = new CustomerRequestDto();

		account = new Account();

		loanInfoRequestDto = new LoanInfoRequestDto();
		loanInfoRequestDto.setAnnualSalary(300000D);
		loanInfoRequestDto.setCustomerId(1);
		loanInfoRequestDto.setLoanAmount(100000D);
		loanInfoRequestDto.setLoanTenure(10F);
		loanInfoRequestDto.setPropertyType("Home");
		loanInfoRequestDto.setPropertyValue(400000D);

		loanDetail = new LoanDetails();
		loanDetail.setCustomerId(1);
		loanDetail.setEmi(35000.0);
		loanDetail.setLoanAccountNumber(8763657238L);
		loanDetail.setLoanAmount(450000.0);
		loanDetail.setLoanId(1);
		loanDetail.setLoanTenure(3F);
		loanDetail.setPropertyType("Home");
		loanDetail.setPropertyValue(8500000.0);
		loanDetail.setRateOfInterest(4F);
		loanDetail.setTotalAmount(600000.0);

		loanRequestDto = new LoanRequestDto();
		loanRequestDto.setTotalAmount(600000.0);
		loanRequestDto.setCustomerId(1);
		loanRequestDto.setLoanAmount(100000D);
		loanRequestDto.setLoanTenure(10F);
		loanRequestDto.setPropertyType("Home");
		loanRequestDto.setPropertyValue(400000D);
		BeanUtils.copyProperties(loanRequestDto, loanDetail);

	}

	@Test
	public void loanInfo() {
		logger.info("inside loan enquiry");
		Optional<Customer> customers = Optional.of(customer);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customers);
		LoanInfoResponseDto loanInfoResponseDto = loanServiceImpl.loanInfo(loanInfoRequestDto);
		Assert.assertNotNull(loanInfoResponseDto);
	}

	@Test(expected = CommonException.class)
	public void testLoanInfo() {
		logger.info("inside loan enquiry");
		customer=null;
		Optional<Customer> customers = Optional.ofNullable(customer);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customers);
		LoanInfoResponseDto loanInfoResponseDto = loanServiceImpl.loanInfo(loanInfoRequestDto);
		Assert.assertNotNull(loanInfoResponseDto);
	}

	
	@Test
	public void testLoanSummary() {
		logger.info("inside loan summary");
		Optional<Customer> customerInfo = Optional.of(customer);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customerInfo);
		Mockito.when(loanRepository.findByCustomerId(Mockito.anyInt())).thenReturn(loanDetail);
		LoanDetails loanInfo = loanServiceImpl.getLoanSummary(1);
		assertNotNull(loanInfo);

	}

	@Test(expected = CommonException.class)
	public void testNullLoanSummary() {
		customer = null;
		Optional<Customer> customerInfo = Optional.ofNullable(customer);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customerInfo);
		LoanDetails loanInfo = loanServiceImpl.getLoanSummary(6);
		assertNull(loanInfo);

	}

	@Test(expected = CommonException.class)
	public void testApplyLoan() {
		logger.info("inside apply loan service test");
		Optional<Customer> customerInfo = Optional.of(customer);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customerInfo);
		Mockito.when(loanRepository.findByCustomerId(Mockito.anyInt())).thenReturn(loanDetail);
		assertNotNull(loanDetail);
		String info = loanServiceImpl.applyLoan(loanRequestDto);
		assertNotNull(info);
		assertEquals(message, info);

	}

	@Test(expected = CommonException.class)
	public void mobileTest() {
		logger.info("inside mobile test");
		customerRequestDto.setMobileNumber(95668384L);
		String info = loanServiceImpl.applyLoan(loanRequestDto);
		assertEquals(message, info);
	}

	@Test(expected = CommonException.class)
	public void emailTest() {
		logger.info("inside mail test");
		customerRequestDto.setEmailId("jyoshna.com");
		String info = loanServiceImpl.applyLoan(loanRequestDto);
		assertEquals(message, info);
	}
}

package com.hcl.mortgage.service;

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

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
/**
 * @author Jyoshna
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountSummaryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AccountSummaryServiceTest.class);

	@Mock
	AccountRepository accountRepository;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	AccountSummaryServiceimpl accountSummaryServiceImpl;

	AccountSummaryResponseDto accountSummaryResponseDto;

	Integer customerId;

	Account account;

	Optional<Customer> customers;

	Customer customer;

	@Before
	public void setup() {

		customerId = 1;

		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("Jyoshna");
		customer.setEmailId("jyoshna@gmail.com");

		account = new Account();
		account.setAccountNumber(84053768334955L);
		account.setAccountBalance(2000D);
		account.setCustomerId(1);

	}

	@Test
	public void testGetAccountSummary() {
		logger.info("inside account summary test");
		Optional<Customer> customers = Optional.of(customer);
		Mockito.when(accountRepository.findByCustomerId(Mockito.anyInt())).thenReturn(account);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customers);
		AccountSummaryResponseDto accountSummaryResponseDto = accountSummaryServiceImpl.getAccountSummary(customerId);
		Assert.assertEquals(Long.valueOf(84053768334955L), accountSummaryResponseDto.getAccountNumber());
		Assert.assertEquals(Double.valueOf(2000D), accountSummaryResponseDto.getAccountBalance());
		Assert.assertEquals("Jyoshna", accountSummaryResponseDto.getCustomerName());
		Assert.assertEquals(Integer.valueOf(201), accountSummaryResponseDto.getStatusCode());
	}

	@Test(expected = CommonException.class)
	public void negativeTestGetAccountSummary() {
		logger.info("inside account summary test");
		customer=null;
		Optional<Customer> customers = Optional.ofNullable(customer);
		Mockito.when(accountRepository.findByCustomerId(Mockito.anyInt())).thenReturn(account);
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyInt())).thenReturn(customers);
		AccountSummaryResponseDto accountSummaryResponseDto = accountSummaryServiceImpl.getAccountSummary(customerId);
		Assert.assertEquals(Long.valueOf(84053768334955L), accountSummaryResponseDto.getAccountNumber());
		Assert.assertEquals(Double.valueOf(2000D), accountSummaryResponseDto.getAccountBalance());
	}
}

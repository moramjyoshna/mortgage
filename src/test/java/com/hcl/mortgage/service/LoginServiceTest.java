package com.hcl.mortgage.service;

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

import com.hcl.mortgage.dto.LoginRequestDto;
import com.hcl.mortgage.dto.LoginResponseDto;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
/**
 * @author Jyoshna
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceTest.class);

	@Mock
	CustomerRepository customerRepository;

	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	Customer customer;

	LoginResponseDto loginResponseDto;

	LoginRequestDto loginRequestDto;

	@Before
	public void setup() {

		customer = new Customer();
		customer.setCustomerId(1);
		customer.setPassword("@^+=&");
		customer.setCustomerName("Jyoshna");
		customer.setEmailId("jyoshna@gmail.com");

		loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmailId("jyoshna@gmail.com");
		loginRequestDto.setPassword("@^+=&");
	}

	@Test
	public void testLogin() {
		logger.info("inside login test");
		Mockito.when(customerRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(customer);
		LoginResponseDto loginResponseDto = loginServiceImpl.login(loginRequestDto);
		loginResponseDto.setMessage("Login Successfull");
		Assert.assertEquals("Login Successfull", loginResponseDto.getMessage());
		Assert.assertEquals(Integer.valueOf(1), loginResponseDto.getCustomerId());
	}

	@Test(expected = CommonException.class)
	public void negativeTestLogin() {
		logger.info("inside login test");
		Mockito.when(customerRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(null);
		LoginResponseDto loginResponseDto = loginServiceImpl.login(loginRequestDto);
		loginResponseDto.setMessage("Login Successfull");
		Assert.assertEquals("Login Successfull", loginResponseDto.getMessage());
		Assert.assertEquals(Integer.valueOf(1), loginResponseDto.getCustomerId());
	}
}

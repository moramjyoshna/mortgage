package com.hcl.mortgage.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.Email;
import com.hcl.mortgage.util.Sms;

/**
 * @author Subashri
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	@Mock
	CustomerRepository customerRepository;
	@Mock
	AccountRepository accountRepository;
	@InjectMocks
	CustomerServiceImpl customerService;
	@Mock
	JavaMailSender javaMailSender;
	String message = null;
	CustomerRequestDto customerRequestDto = new CustomerRequestDto();
	Customer customer = new Customer();
	Account account = new Account();
	Sms sms = new Sms();
	Email email = new Email();

	@Before
	public void initData() {
		customerRequestDto.setCustomerName("Subashri");
		customerRequestDto.setDob("1997/07/09");
		customerRequestDto.setEmailId("subasridharan0@gmail.com");
		customerRequestDto.setMobileNumber(9566838401L);
		message = "Registered Successfully";
		BeanUtils.copyProperties(customerRequestDto, customer);
		account.setAccountBalance(10000.0);
		account.setAccountNumber(7632360839L);
		account.setCustomerId(1);

	}

	@Test
	public void testRegisterCustomer() {
		logger.info("inside register customer service test");
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
		String info = customerService.registerCustomer(customerRequestDto);
		assertNotNull(info);
		assertEquals(message, info);
	}

	@Test(expected = CommonException.class)
	public void ageTest() {
		logger.info("inside age test");
		customerRequestDto.setDob("2002/04/02");
		String info = customerService.registerCustomer(customerRequestDto);
		assertEquals(message, info);
	}

	@Test(expected = CommonException.class)
	public void mobileTest() {
		logger.info("inside mobile test");
		customerRequestDto.setMobileNumber(2345L);
		String info = customerService.registerCustomer(customerRequestDto);
		assertEquals(message, info);
	}

	@Test(expected = CommonException.class)
	public void emailTest() {
		logger.info("inside mail test");
		customerRequestDto.setEmailId("kabhil.com");
		String info = customerService.registerCustomer(customerRequestDto);
		assertEquals(message, info);
	}
}

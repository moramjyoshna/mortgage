package com.hcl.mortgage.controller;

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
import org.springframework.http.ResponseEntity;

import com.hcl.mortgage.dto.CustomerRequestDto;
import com.hcl.mortgage.exception.Response;
import com.hcl.mortgage.service.CustomerServiceImpl;

/**
 * @author Subashri
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);
	@Mock
	CustomerServiceImpl customerService;
	@InjectMocks
	CustomerController customerController;
	CustomerRequestDto customerRequestDto = new CustomerRequestDto();
	String message = null;

	@Before
	public void initData() {
		customerRequestDto.setCustomerName("Subashri");
		customerRequestDto.setDob("1997/07/09");
		customerRequestDto.setEmailId("subasridharan0@gmail.com");
		customerRequestDto.setMobileNumber(9566838401L);
		message = "Registered Successfully";
	}

	@Test
	public void testCustomerRegistration() {
		logger.info("inside customer register controller test");
		Mockito.when(customerService.registerCustomer(Mockito.any())).thenReturn(message);
		ResponseEntity<Response> customerResponse = customerController
				.customerRegistration(customerRequestDto);
		assertNotNull(customerResponse);
		assertEquals(message, customerResponse.getBody().getMessage());
	}
}

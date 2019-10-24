package com.hcl.mortgage.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.service.AccountSummaryService;

/**
 * @author Jyoshna
 *
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class AccountSummaryControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountSummaryControllerTest.class);
	@InjectMocks
	AccountSummaryController accountSummaryController;

	@Mock
	AccountSummaryService accountSummaryService;

	AccountSummaryResponseDto accountSummaryResponseDto;

	MockMvc mockMvc;

	Integer customerId;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountSummaryController).build();
		customerId = 1;
		accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setAccountNumber(84053768334955L);
		accountSummaryResponseDto.setAccountBalance(3000D);
		accountSummaryResponseDto.setCustomerName("Jyoshna");
		accountSummaryResponseDto.setStatusCode(201);
		accountSummaryResponseDto.setMessage("Success");

	}

	@Test
	public void testGetAccountSummary() throws Exception {
		logger.info("inside account summary cotroller test");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{customerId}", 1)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}

package com.hcl.mortgage.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.mortgage.dto.LoanInfoRequestDto;
import com.hcl.mortgage.dto.LoanRequestDto;
import com.hcl.mortgage.entity.LoanDetails;
import com.hcl.mortgage.exception.Response;
import com.hcl.mortgage.service.LoanService;
import com.hcl.mortgage.service.LoanServiceImpl;

/**
 * @author Jyoshna, Subashri
 *
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class LoanControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
	@Mock
	LoanServiceImpl loanService;

	@InjectMocks
	LoanController loanController;

	@Mock
	LoanService loanServcie;

	LoanInfoRequestDto loanInfoRequestDto;

	LoanRequestDto loanRequestDto;

	LoanDetails loanDetail;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();

		loanInfoRequestDto = new LoanInfoRequestDto();
		loanInfoRequestDto.setAnnualSalary(100000.0);
		loanInfoRequestDto.setCustomerId(1);
		loanInfoRequestDto.setLoanAmount(500000D);
		loanInfoRequestDto.setLoanTenure(2F);
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
		loanRequestDto.setCustomerId(1);
		loanRequestDto.setEmi(35000.0);
		loanRequestDto.setLoanAmount(450000.0);
		loanRequestDto.setLoanTenure(3F);
		loanRequestDto.setPropertyType("Home");
		loanRequestDto.setPropertyValue(8500000.0);
		loanRequestDto.setTotalAmount(600000.0);

	}

	@Test
	public void testInfo() throws Exception {
		logger.info("inside loan enquiry controller test");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/loanInfo").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(loanInfoRequestDto)))
				.andExpect(status().isCreated());
	}

	public static String asJsonString(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test(expected = NullPointerException.class)
	public void testLoanSummary() {
		logger.info("inside loan summary controller test");
		// Mockito.when(loanService.getLoanSummary(Mockito.anyInt())).thenReturn(loanDetail);
		ResponseEntity<LoanDetails> loan = loanController.loanSummary(1);
		assertNotNull(loan);
		assertEquals(200, loan.getStatusCode().value());

	}

	@Test
	public void testApplyLoan() {
		logger.info("inside apply loan controller test");
		ResponseEntity<Response> loanResponse = loanController.applyLoan(loanRequestDto);
		assertNotNull(loanResponse);
		assertEquals(201, loanResponse.getBody().getStatusCode());
	}
}

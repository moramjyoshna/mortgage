package com.hcl.mortgage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.mortgage.service.AccountSummaryServiceimpl;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

public class Sms {
	private static final Logger logger = LoggerFactory.getLogger(AccountSummaryServiceimpl.class);

	public void sendSms(Long mobileNumber, long accountNumber, String randomPassword, String accountType) {
		String mobile = Long.toString(mobileNumber);
		mobile = "91" + mobile;
		logger.info(mobile);
		NexmoClient client = NexmoClient.builder().apiKey("560e12ca").apiSecret("1dEXo2Cofkam2MS4").build();

		SmsSubmissionResponse responses = client.getSmsClient()
				.submitMessage(new TextMessage("NEXMO", mobile, "Your Modal Bank " + accountType + " Account Number is "
						+ accountNumber + " and the password is " + randomPassword));

		for (SmsSubmissionResponseMessage response : responses.getMessages()) {
        logger.debug("message",response);
		}
	}
}
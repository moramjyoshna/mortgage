package com.hcl.mortgage.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Email {
	
	public void sendEmail(String emailId, long accountNumber, String randomPassword,JavaMailSender javaMailSender,String accountType) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailId);
		mail.setSubject("Registered Account Information- Account Number & Password");
		mail.setText("Your Modal Bank "+accountType+" Account Number is "+accountNumber +" and the password is "+randomPassword);
        javaMailSender.send(mail);
        
	}
	
}

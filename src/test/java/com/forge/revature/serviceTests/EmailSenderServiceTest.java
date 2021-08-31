package com.forge.revature.serviceTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;
import com.forge.revature.services.EmailSenderService;

@SpringBootTest
public class EmailSenderServiceTest {
	
	@MockBean
	JavaMailSender javaMailSender;

	private EmailSenderService emailSenderService;
	
	private User user;

	private Portfolio portfolioNotSubmitted, portfolioSubmitted;

	@BeforeEach
	public void setup() {
		emailSenderService = new EmailSenderService(javaMailSender);
		
		user = new User(1, "Hong", "Wu", "hong@hong.com", "password", true);

		portfolioNotSubmitted = new Portfolio(1, "hong Portfolio", user, false, false, false, user, "description", true, true, "", "", 0L, null);
		
		portfolioSubmitted = new Portfolio(1, "hong Portfolio", user, true, false, false, user, "description", true, true, "", "", 0L, null);
	}
	
	@Test
	public void sendStatusEmailNotReviewedTest() {
		assertDoesNotThrow(()->emailSenderService.sendStatusEmail(user, portfolioNotSubmitted));
		assertDoesNotThrow(()->emailSenderService.sendStatusEmail(user, portfolioSubmitted));
	}
}

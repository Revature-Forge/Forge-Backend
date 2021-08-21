package com.forge.revature.services;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	String htmlApproved = ""
	public void sendStatusEmail(User user, Portfolio portfolio) {
		
		try {
			if(portfolio.isReviewed() && !portfolio.isSubmitted()) {
				MimeMessage message = mailSender.createMimeMessage();
				String feedback="";
				message.setFrom("forge.test@outlook.com");
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));	
	        	if(portfolio.isApproved()) {
	        		message.setSubject("Portfolio Submission Approved");
	        		message.setContent(htmlApproved, "text/html");
	        	}
	        	else {
	        		Iterator map = portfolio.getFlags().entrySet().iterator();
	        		StringBuilder mapAsString = new StringBuilder("<h3 style=\"text-align:left\">Feedback</h3><ulstyle=\"text-align:left\">");
	        		while(map.hasNext()) {
	        			Map.Entry pair = (Map.Entry)map.next();
	        			if(pair.getValue().toString().length() > 0) {
	        				System.out.println(pair.getValue().toString().length());
	        				mapAsString.append("<li>"+pair.getKey()+" : "+pair.getValue()+"</li>");
	        			}
	        				
	        		}
	        		mapAsString.append("</ul>");
	        		feedback = mapAsString.toString();
	        		String htmlDenied = "";
	        		message.setSubject("There's an issue with your submitted portfolio");
	        		message.setContent(htmlDenied, "text/html");
	        	}
		        mailSender.send(message);
				System.out.println(feedback+message.getSubject());	
			}
			

		}catch (MessagingException response) {
            response.printStackTrace();
        }
		
		
	}
}

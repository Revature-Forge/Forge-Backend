package com.forge.revature.services;

import java.util.Iterator;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendStatusEmail(User user, Portfolio portfolio) {

		try {
			if (portfolio.isReviewed() && !portfolio.isSubmitted()) {
				MimeMessage message = mailSender.createMimeMessage();
				message.setFrom("forge.test@outlook.com");
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				String html;
				if (portfolio.isApproved()) {
					message.setSubject("Portfolio Submission Approved");
					html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd><html lang=\"en\"><head><meta charset=\"UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta name=\"x-apple-disable-message-reformatting\"><title></title><!--[if lte mso 16]> <noscript> <xml> <o:OfficeDocumentSettings> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> </noscript><![endif]--><style>table, td, div, h1, p {font-family: Arial, sans-serif;}</style></head><body style=\"margin:0;padding:0;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\" align=\"center\"><tr><td align=\"center\" style=\"padding:0;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border-spacing:0;text-align:left;\"><tr><td align=\"center\" style=\"padding:40px 0 0 0;background: #121212; color:#ffffff;\"><table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td align=\"right\" style=\"padding:20px 10px\"><table><tr><td><img src=\"cid:logo\" alt=\"Revature\" width=\"300\" style=\"height:auto;display:block;\"></td></tr></table></td></tr><tr><td align=\"left\" style=\"padding:0 0 0 30px;background: #121212;\"><table><tr><td style=\"color:#FFFFFF\">"
							+ message.getSubject()
							+ " | RevaturePro</td></tr></table></td></tr><tr><td><table style=\"width:660px;min-width:600px;background: #121212;\"><tr><td style=\"width: 100%; height: 5px; background: #F26925; overflow: hidden;\"></td></tr></table></td></tr></table></td></tr><tr><td style=\"padding:36px 30px 42px 30px;background: #121212;\"><table role=\"presentation\" style=\"width:600px;color:#ffffff;\"><tr><td style=\"color:#ffffff;\"><h1 style=\"color: #FFFFFF;\">"
							+ message.getSubject() + " | RevaturePro</h1><h3 style=\"color: #FFFFFF;\">Hello "
							+ user.getFName() + " " + user.getLName()
							+ "</h3><p style=\"color: #FFFFFF;\">Your portfolio, "
							+ portfolio.getName()
							+ ", has been approved by your portfolio manager.</p><br><span style=\"color: #FFFFFF;\">Use this link to browse your portfolios:</span> <a href=\"http://track.revature.com/track/click/30470371/app.revature.com?p=eyJzIjoiOGowbmRJTGctN1Ryd2FKcHk2ejRoYkVrQmJnIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2FwcC5yZXZhdHVyZS5jb21cXFwvcHJvZmlsZVxcXC90YWJcXFwvcG9ydGZvbGlvc1wiLFwiaWRcIjpcImI5ODZlM2FmZTc3YTQwNmNiZjg3ZjM1OTkwYjc3MDU0XCIsXCJ1cmxfaWRzXCI6W1wiNTNlMmJlZmY4YjRkYTVlNzcyNzQxNWI3MzRiMDQ2ODU2ODJlMWE0MlwiXX0ifQ\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"1\">https://app.revature.com/profile/tab/portfolios</a><br><br><p style=\"color: #FFFFFF;\">Regards<br><strong>RevaturePro</strong></p> <br><a href=\"http://track.revature.com/track/click/30470371/www.revature.com?p=eyJzIjoiZE5fT294MHNuY3BDS3JBXzdzaVFkLXNWZmxRIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3d3dy5yZXZhdHVyZS5jb21cIixcImlkXCI6XCJiOTg2ZTNhZmU3N2E0MDZjYmY4N2YzNTk5MGI3NzA1NFwiLFwidXJsX2lkc1wiOltcImYzMzAwNzEwM2Y4MmM1NjdhOTcwODk3OGE1MjI3MjczN2I1YmFkOGNcIl19In0\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"2\">https://www.revature.com</a></td></tr></table></td></tr><tr><td style=\"padding:0;background: #121212; color:#ffffff;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td><table role=\"presentation\" style=\"padding:10px 0;width:600px;border-collapse:collapse;border:0;border-spacing:0;\" align=\"center\"><tr><td><table style=\"padding:0 0 0 10px;width:38px;\"><tr><td><a href=\"https://www.facebook.com/revature/\" target=\"_blank\"><img src=\"cid:facebook-logo\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://twitter.com/WeAreRevature\" target=\"_blank\"><img src=\"cid:twitter-logo\" alt=\"Twitter\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.linkedin.com/company/revature/mycompany/\" target=\"_blank\"><img src=\"cid:linkedin-logo\"alt=\"LinkedIn\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.instagram.com/teamrevature/\" target=\"_blank\"><img src=\"cid:instagram-logo\"alt=\"Instagram\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.youtube.com/channel/UCGBKIv2WIS2utCQwJFqYFAg/videos\" target=\"_blank\"><img src=\"cid:youtube-logo\" alt=\"Youtube\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.tiktok.com/@revature\" target=\"_blank\"><img src=\"cid:tiktok-logo\" alt=\"Tiktok\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://revature.com/\" target=\"_blank\"><img src=\"cid:linkto-icon\" alt=\"Revature\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"mailto:support@revature.com\" target=\"_blank\"><img src=\"cid:mailto-icon\" alt=\"Mailto\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td></tr></table></td></tr><tr><td><table style=\"width:660px;min-width:100%;background: #121212;\"><tr><td style=\"width: 100%; height: 5px; background: #F26925; overflow: hidden;\"></td></tr></table></td></tr><tr><td><table style=\"width:600px;padding:20px;min-width:600px;background: #121212;font-size: small;\"><tr><td><p style=\"color: #D3D3D3;\" align=\"center\">Copyright &copy; 2021 Revature, All rights reserved.</p><br><p style=\"color: #FFFFFF;\" align=\"center\"><b>Our mailing address:</b></p><p style=\"text-decoration: underline;color: #D3D3D3;\" align=\"center\">11730 Plaza America Dr,Suite 205, Reston VA 20190, USA</p><p style=\"text-decoration: underline;color: #D3D3D3;\" align=\"center\">W: https://revature.com |P: 703 570 8181 | E: info@revature.com </p></td></tr></table></td></tr></table></td></tr></table></td></tr></table></body></html>";

				} else {
					Iterator map = portfolio.getFlags().entrySet().iterator();
					StringBuilder mapAsString = new StringBuilder(
							"<h3 style=\"color:#ffffff;\" align=\"left\">Feedback</h3><ul style=\"color:#ffffff;\" align=\"left\">");
					while (map.hasNext()) {
						Map.Entry pair = (Map.Entry) map.next();
						if (pair.getValue().toString().length() > 0) {
							System.out.println(pair.getValue().toString().length());
							mapAsString.append("<li>" + pair.getKey() + " : " + pair.getValue() + "</li>");
						}
					}
					mapAsString.append("</ul>");
					String feedback = mapAsString.toString();
					message.setSubject("There's an issue with your submitted portfolio");
					html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd><html lang=\"en\"><head><meta charset=\"UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta name=\"x-apple-disable-message-reformatting\"><title></title><!--[if lte mso 16]> <noscript> <xml> <o:OfficeDocumentSettings> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> </noscript><![endif]--><style>table, td, div, h1, p {font-family: Arial, sans-serif;}</style></head><body style=\"margin:0;padding:0;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\" align=\"center\"><tr><td align=\"center\" style=\"padding:0;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border-spacing:0;text-align:left;\"><tr><td align=\"center\" style=\"padding:40px 0 0 0;background: #121212; color:#ffffff;\"><table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td align=\"right\" style=\"padding:20px 10px\"><table><tr><td><img src=\"cid:logo\" alt=\"Revature\" width=\"300\" style=\"height:auto;display:block;\"></td></tr></table></td></tr><tr><td align=\"left\" style=\"padding:0 0 0 30px;background: #121212;\"><table><tr><td style=\"color:#FFFFFF\">"
							+ message.getSubject()
							+ " | RevaturePro</td></tr></table></td></tr><tr><td><table style=\"width:660px;min-width:600px;background: #121212;\"><tr><td style=\"width: 100%; height: 5px; background: #F26925; overflow: hidden;\"></td></tr></table></td></tr></table></td></tr><tr><td style=\"padding:36px 30px 42px 30px;background: #121212;\"><table role=\"presentation\" style=\"width:600px;color:#ffffff;\"><tr><td style=\"color:#ffffff;\"><h1 style=\"color: #FFFFFF;\">"
							+ message.getSubject() + " | RevaturePro</h1><h3 style=\"color: #FFFFFF;\">Hello "
							+ user.getFName() + " " + user.getLName()
							+ ",</h3><p style=\"color: #FFFFFF;\">Your portfolio manager thinks that your portfolio, "
							+ portfolio.getName() + ", could use more work.</p>" + feedback
							+ "<br><span style=\"color: #FFFFFF;\">Use this link to browse your portfolios:</span> <a href=\"http://track.revature.com/track/click/30470371/app.revature.com?p=eyJzIjoiOGowbmRJTGctN1Ryd2FKcHk2ejRoYkVrQmJnIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2FwcC5yZXZhdHVyZS5jb21cXFwvcHJvZmlsZVxcXC90YWJcXFwvcG9ydGZvbGlvc1wiLFwiaWRcIjpcImI5ODZlM2FmZTc3YTQwNmNiZjg3ZjM1OTkwYjc3MDU0XCIsXCJ1cmxfaWRzXCI6W1wiNTNlMmJlZmY4YjRkYTVlNzcyNzQxNWI3MzRiMDQ2ODU2ODJlMWE0MlwiXX0ifQ\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"1\">https://app.revature.com/profile/tab/portfolios</a><br><br><p style=\"color: #FFFFFF;\">Regards<br><strong>RevaturePro</strong></p> <br><a href=\"http://track.revature.com/track/click/30470371/www.revature.com?p=eyJzIjoiZE5fT294MHNuY3BDS3JBXzdzaVFkLXNWZmxRIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3d3dy5yZXZhdHVyZS5jb21cIixcImlkXCI6XCJiOTg2ZTNhZmU3N2E0MDZjYmY4N2YzNTk5MGI3NzA1NFwiLFwidXJsX2lkc1wiOltcImYzMzAwNzEwM2Y4MmM1NjdhOTcwODk3OGE1MjI3MjczN2I1YmFkOGNcIl19In0\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"2\">https://www.revature.com</a></td></tr></table></td></tr><tr><td style=\"padding:0;background: #121212; color:#ffffff;\"><table role=\"presentation\" style=\"width:600px;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td><table role=\"presentation\" style=\"padding:10px 0;width:600px;border-collapse:collapse;border:0;border-spacing:0;\" align=\"center\"><tr><td><table style=\"padding:0 0 0 10px;width:38px;\"><tr><td><a href=\"https://www.facebook.com/revature/\" target=\"_blank\"><img src=\"cid:facebook-logo\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://twitter.com/WeAreRevature\" target=\"_blank\"><img src=\"cid:twitter-logo\" alt=\"Twitter\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.linkedin.com/company/revature/mycompany/\" target=\"_blank\"><img src=\"cid:linkedin-logo\"alt=\"LinkedIn\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.instagram.com/teamrevature/\" target=\"_blank\"><img src=\"cid:instagram-logo\"alt=\"Instagram\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.youtube.com/channel/UCGBKIv2WIS2utCQwJFqYFAg/videos\" target=\"_blank\"><img src=\"cid:youtube-logo\" alt=\"Youtube\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://www.tiktok.com/@revature\" target=\"_blank\"><img src=\"cid:tiktok-logo\" alt=\"Tiktok\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"https://revature.com/\" target=\"_blank\"><img src=\"cid:linkto-icon\" alt=\"Revature\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td><td><table style=\"padding:0 0 0 25px;width:38px;\"><tr><td><a href=\"mailto:support@revature.com\" target=\"_blank\"><img src=\"cid:mailto-icon\" alt=\"Mailto\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td></tr></table></td></tr><tr><td><table style=\"width:660px;min-width:100%;background: #121212;\"><tr><td style=\"width: 100%; height: 5px; background: #F26925; overflow: hidden;\"></td></tr></table></td></tr><tr><td><table style=\"width:600px;padding:20px;min-width:600px;background: #121212;font-size: small;\"><tr><td><p style=\"color: #D3D3D3;\" align=\"center\">Copyright &copy; 2021 Revature, All rights reserved.</p><br><p style=\"color: #FFFFFF;\" align=\"center\"><b>Our mailing address:</b></p><p style=\"text-decoration: underline;color: #D3D3D3;\" align=\"center\">11730 Plaza America Dr,Suite 205, Reston VA 20190, USA</p><p style=\"text-decoration: underline;color: #D3D3D3;\" align=\"center\">W: https://revature.com |P: 703 570 8181 | E: info@revature.com </p></td></tr></table></td></tr></table></td></tr></table></td></tr></table></body></html>";

				}
				MimeMultipart multipart = new MimeMultipart("related");
				// first part (the html)
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(html, "text/html");

				// add html
				multipart.addBodyPart(messageBodyPart);

				// second part (the image)
				// Revature Logo
				messageBodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource("./src/main/resources/images/rev-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<logo>");
				messageBodyPart.setFileName("rev-logo.png");
				// add image to multipart
				multipart.addBodyPart(messageBodyPart);
				// Facebook logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/facebook-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<facebook-logo>");
				messageBodyPart.setFileName("facebook-logo.png");

				multipart.addBodyPart(messageBodyPart);
				// Twitter logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/twitter-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<twitter-logo>");
				messageBodyPart.setFileName("twitter-logo.png");
				multipart.addBodyPart(messageBodyPart);
				// LinkedIn logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/linkedin-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<linkedin-logo>");
				messageBodyPart.setFileName("linkedin-logo.png");
				multipart.addBodyPart(messageBodyPart);
				// Instagram logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/instagram-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<instagram-logo>");
				messageBodyPart.setFileName("instagram-logo.png");
				multipart.addBodyPart(messageBodyPart);
				// Youtube logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/youtube-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<youtube-logo>");
				messageBodyPart.setFileName("youtube-logo.png");
				multipart.addBodyPart(messageBodyPart);
				// Facebook logo
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/tiktok-logo.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<tiktok-logo>");
				messageBodyPart.setFileName("tiktok-logo.png");
				multipart.addBodyPart(messageBodyPart);
				// Linkto icon
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/linkto-icon.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<linkto-icon>");
				messageBodyPart.setFileName("linkto-icon.png");
				multipart.addBodyPart(messageBodyPart);
				// Mailto icon
				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource("./src/main/resources/images/mailto-icon.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<mailto-icon>");
				messageBodyPart.setFileName("mailto-icon.png");
				multipart.addBodyPart(messageBodyPart);

				// put everything together
				message.setContent(multipart);

				mailSender.send(message);
				System.out.println("Mail Sent");

			}

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}

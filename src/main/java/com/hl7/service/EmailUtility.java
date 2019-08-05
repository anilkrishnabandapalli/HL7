package com.hl7.service;

import java.io.File;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtility {

	private static Logger log = LoggerFactory.getLogger(EmailUtility.class);

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${application.savepath}")
	private String path;
	
	@Value("${spring.mail.username}")
	private String mailFrom;

	public void sendMail(String mailTo, String subject, String body, String fileName) {

		LocalDate date = LocalDate.now();
		String homeDir = System.getProperty("user.home");
		
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom(new InternetAddress(mailFrom, "Do not reply"));
			helper.setTo(mailTo);

			helper.setSubject(subject);

			helper.setText(body, true);

			helper.addAttachment("Report.pdf", new File(homeDir + "/" + path + "/" + date.toString() + "/"+fileName));

			javaMailSender.send(message);

		} catch (MessagingException e) {
			log.error("Error sending mail");
			log.error("Exception : ", e);
		} catch (Exception e) {
			log.error("Error sending mail");
			log.error("Exception : ", e);
		}

	}

}

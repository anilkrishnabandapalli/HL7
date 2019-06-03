package com.hl7.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailUtility {

	private static Logger log = LoggerFactory.getLogger(EmailUtility.class);

	public void sendMail(String sendTo, String subject, String message, String fileName) {

//		Setup SMTP 
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("radiologyreport@yahoo.com", "963852123");
			}
		});

		log.debug("Sending Mail to {}", sendTo);

//		session.setDebug(true);

//		Create Message
		Message mail = new MimeMessage(session);
		try {
			mail.setFrom(new InternetAddress("radiologyreport@yahoo.com", "Do not reply"));
			mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
			mail.setSubject(subject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(message, "text/html");

			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.attachFile(new File(fileName));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(attachmentBodyPart);

			mail.setContent(multipart);

//			Send Message
			Transport.send(mail);

		} catch (UnsupportedEncodingException e) {
			log.error("Exception occured : ", e);
		} catch (MessagingException e) {
			log.error("Exception occured : ", e);
		} catch (IOException e) {
			log.error("Exception occured : ", e);
		}
	}
}

package com.hl7.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Hl7MessageHandler {

	@Autowired
	ORMConverter ormConverter;
	
	@Value("${application.savepath}")
	private String path;

	private static Logger log = LoggerFactory.getLogger(Hl7MessageHandler.class);

	public void saveReceivedMessage(String receivedMessage) {

//		Creating dir if not exist
		LocalDate date = LocalDate.now();
		String homeDir=System.getProperty("user.home");
		
		File dir = new File(homeDir+"/"+path+"/"+ date.toString() + "/");
		if (!dir.exists())
			dir.mkdirs();

//		creating file and writing HL7 Message into it
		File file = new File(homeDir+"/"+path+"/" + date.toString() + "/test.txt");

		try (FileWriter writer = new FileWriter(file)){
			writer.write(receivedMessage);
			writer.flush();
			log.info("Saved received message at {}",file);
		} catch (IOException e) {
			log.error("Error saving received message");
			log.error("Exception occured :", e);
		}
	}

	public void processReceivedMessage(String receivedMessage) {

		receivedMessage = receivedMessage.replace("\n", "\r\n");
		saveReceivedMessage(receivedMessage);
		ormConverter.convertReceivedMessage(receivedMessage);

	}
}

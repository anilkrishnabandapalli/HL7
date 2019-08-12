package com.hl7.firstPackage;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.hl7.service.Hl7MessageHandler;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

@RestController
@Scope("prototype")
public class HL7Runnable extends Thread  implements ReceivingApplication  {

	protected Socket clientSocket = null;
		@Autowired
		Hl7MessageHandler hl7MessageHandler;
	public HL7Runnable(Socket clientSocket, Hl7MessageHandler hl7MessageHandler) {
		this.clientSocket = clientSocket;
		this.hl7MessageHandler = hl7MessageHandler;
	}

	public void run() {
		InputStream input = null;
		OutputStream output = null;
		try {
				input = clientSocket.getInputStream();
				output = clientSocket.getOutputStream();
				long time = System.currentTimeMillis();
				output.write(("HTTP/1.1 200 OK\n\n Shaila Associates received your request!").getBytes());
				System.out.println("eCW Client Request processed at: " + time);
				
				String receivedMessage = displayMessage(input);
				System.out.println("Recieved Input is " + receivedMessage);
				
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	private String displayMessage(InputStream input) {
		
		String currentDateTimeString = getCurrentTimeStamp();
		Random rand = new Random();
		byte[] messageByte = new byte[1000];
		String dataString = "";
		try 
		{
		        int bytesRead = input.read(messageByte);
		        dataString += new String(messageByte, 0, bytesRead);
		   
		    System.out.println("MESSAGE: " + dataString);
		    
		    File file = new File("c://Users//Shaila Cholli//Desktop//HL7 Orders//"+currentDateTimeString+rand.nextInt(100)+".txt");
			FileWriter writer = new FileWriter(file);
			writer.write(dataString);
			writer.close();
			int index = dataString.indexOf("MSH");
			hl7MessageHandler.processReceivedMessage(dataString.substring(index, dataString.length()));
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		return dataString;
	}
	
	private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

	@Override
	public Message processMessage(Message theMessage, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canProcess(Message theMessage) {
		// TODO Auto-generated method stub
		return false;
	}
}

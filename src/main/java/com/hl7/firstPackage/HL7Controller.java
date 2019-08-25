package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hl7.service.Hl7MessageHandler;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

@RestController
public class HL7Controller implements ReceivingApplication {
	

	@Autowired
	Hl7MessageHandler hl7MessageHandler;
	
	private static Logger log=LoggerFactory.getLogger(HL7Controller.class);
	
	@RequestMapping(value="/")
    public void indexPost() throws ReceivingApplicationException, HL7Exception,IOException {
		
		final ExecutorService clientRequestPool = Executors.newFixedThreadPool(10);
		//log.debug("Message received is : {}",receivedMessage);
		HL7TCPServer hl7TCPServer = new HL7TCPServer();
		ServerSocket serverSocket = new ServerSocket(8088);
		hl7TCPServer.startServer(serverSocket, hl7MessageHandler);
		/*
		 * try { ServerSocket serverSocket = new ServerSocket(8088);
		 * System.out.println("Waiting for eCW Client to connect..."); InputStream input
		 * = null; OutputStream output = null; while (true) { Socket clientSocket =
		 * serverSocket.accept(); input = clientSocket.getInputStream(); output =
		 * clientSocket.getOutputStream(); long time = System.currentTimeMillis();
		 * output.write(("HTTP/1.1 200 OK\n\n Shaila Associates received your request!")
		 * .getBytes()); System.out.println("eCW Client Request processed at: " + time);
		 * 
		 * String receivedMessage1 = displayMessage(input);
		 * System.out.println("Recieved Input is " + receivedMessage1);
		 * 
		 * hl7MessageHandler.processReceivedMessage(receivedMessage1);
		 * 
		 * } } catch (IOException e) {
		 * System.err.println("Unable to process ceCW Client request");
		 * e.printStackTrace(); }
		 */
		
    }
	
	@RequestMapping(value="/view", method = RequestMethod.POST)
	public void processData(@RequestBody String body) {
		//System.out.println(body);
		try {
			String newString = URLDecoder.decode(body, "UTF-8");
			System.out.println(newString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	
//	@RequestMapping(value="/", method = RequestMethod.GET)
//    public String indexGet(@RequestBody String str) {
//		System.out.println("HI");
//        return "Greetings from Spring Boot!";
//    }

	@RequestMapping("/redirectWithRedirectView")
	public void redirectWithUsingRedirectView(){
		try
		{
			System.out.println("Hi");
	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        String s = bufferRead.readLine();
	        System.out.println(s);
		}
		catch(IOException e)
	    {
	        e.printStackTrace();
	    }
}
//	@RequestMapping(value="/", method = RequestMethod.POST)
//	HttpServletRequest request, HttpServletResponse response
//    public String indexPost(@RequestBody String str) throws IOException {
//		System.out.println("Hi and the Message we got is "+str);
//		File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
//		FileWriter writer = new FileWriter(file);
//		writer.write(str);
//		writer.close();
//        return "Greetings from Spring Boot!";
//    }
	
	/*
	 * @RequestMapping(value="/", method = RequestMethod.POST) public Message
	 * indexPost(@RequestBody Message receivedMessage) throws
	 * ReceivingApplicationException, HL7Exception,IOException { String
	 * receivedEncodedMessage = context.getPipeParser().encode(receivedMessage);
	 * System.out.println("Hi and the Message we got is "+receivedEncodedMessage);
	 * File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
	 * FileWriter writer = new FileWriter(file);
	 * writer.write(receivedEncodedMessage); writer.close(); try { return
	 * receivedMessage.generateACK(); }catch (IOException e) { throw new
	 * HL7Exception(e); } }
	 */

//	@RequestMapping(value="/", method = RequestMethod.POST)
//    public Message processMessage(@RequestBody Message receivedMessage) throws ReceivingApplicationException, HL7Exception,IOException {
//		String receivedEncodedMessage = context.getPipeParser().encode(receivedMessage);
//		System.out.println("Hi and the Message we got is "+receivedEncodedMessage);
//		File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
//		FileWriter writer = new FileWriter(file);
//		writer.write(receivedEncodedMessage);
//		writer.close();
//		try {
//        return receivedMessage.generateACK();
//		}catch (IOException e) {
//            throw new HL7Exception(e);
//        }
//    }
	
	
//	
//	@RequestMapping(value="/", method = RequestMethod.POST)
//    public String indexPost(@RequestBody String receivedMessage) throws ReceivingApplicationException, HL7Exception,IOException {
//		 String currentDateTimeString = getCurrentTimeStamp();
//		System.out.println("Hi and the Message we got is "+receivedMessage);
//		File file = new File("c://Users//Shaila Cholli//Desktop//HL7 Orders//"+currentDateTimeString+".txt");
//		FileWriter writer = new FileWriter(file);
//		writer.write(receivedMessage);
//		writer.close();
//		
//		return "Recieved HL7 Request";
//		
//    }
	private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

//	@RequestMapping(value="/", method = RequestMethod.GET)
//    public String indexGet() {
//		System.out.println("HI");
//        return "Greetings from Spring Boot!";
//    }

	@Override
	public Message processMessage(Message theMessage, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		return null;
	}

	@Override
	public boolean canProcess(Message theMessage) {
		return true;
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
		   
		    //System.out.println("MESSAGE: " + dataString);
		    
		    File file = new File("c://Users//Shaila Cholli//Desktop//HL7 Orders//"+currentDateTimeString+rand.nextInt(100)+".txt");
			FileWriter writer = new FileWriter(file);
			writer.write(dataString);
			writer.close();
			
			hl7MessageHandler.processReceivedMessage(dataString);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		return dataString;
	}
}

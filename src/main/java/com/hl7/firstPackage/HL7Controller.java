package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

@ComponentScan
@RestController
public class HL7Controller implements ReceivingApplication {
	
	private static HapiContext context = new DefaultHapiContext();

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
	
	@RequestMapping(value="/", method = RequestMethod.POST)
    public Message indexPost1(@RequestBody Message receivedMessage) throws ReceivingApplicationException, HL7Exception,IOException {
		String receivedEncodedMessage = context.getPipeParser().encode(receivedMessage);
		System.out.println("Hi and the Message we got is "+receivedEncodedMessage);
		File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
		FileWriter writer = new FileWriter(file);
		writer.write(receivedEncodedMessage);
		writer.close();
		try {
        return receivedMessage.generateACK();
		}catch (IOException e) {
            throw new HL7Exception(e);
        }
    }
	
	
	
//	@RequestMapping(value="/", method = RequestMethod.POST)
//    public void indexPost(@RequestBody String receivedMessage) throws ReceivingApplicationException, HL7Exception,IOException {
//		System.out.println("Hi and the Message we got is "+receivedMessage);
//		File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
//		FileWriter writer = new FileWriter(file);
//		writer.write(receivedMessage);
//		writer.close();
//		
//    }
	
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String indexGet(@RequestBody String str) {
		System.out.println("HI");
        return "Greetings from Spring Boot!";
    }

	@Override
	public Message processMessage(Message theMessage, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		return null;
	}

	@Override
	public boolean canProcess(Message theMessage) {
		return true;
	}
}

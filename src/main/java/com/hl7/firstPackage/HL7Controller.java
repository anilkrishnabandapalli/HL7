package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan
@RestController
public class HL7Controller {

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
    public String indexPost(@RequestBody String str , HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Hi and the Message we got is "+request);
		File file = new File("c://Users//Shaila Cholli//Desktop//testFile1.txt");
		FileWriter writer = new FileWriter(file);
		writer.write(str);
		writer.close();
		
		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().flush();
		
		
        return "Greetings from Spring Boot!";
    }
	
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String indexGet(@RequestBody String str) {
		System.out.println("HI");
        return "Greetings from Spring Boot!";
    }
}

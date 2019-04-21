package com.hl7.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}

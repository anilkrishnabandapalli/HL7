package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HL7RequestParsing {

	
	public static void main(String[] args) {
		File file = null;
		
		Map<String, String> messageHeaderMap = new HashMap<String, String>();
		
		try
		{

            file = new File("/Users/anilbandapalli/DEVHOME/Anil/Files/test1.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String text; 
            while ((text = br.readLine()) != null) {
            	if(text.contains("MSH")){
            		extractMessageHeader(text);
            	}
            }
               
                      
		}catch (Exception exep) {
            exep.printStackTrace();
        }
	}

	public static void extractMessageHeader(String text) {
		List<String> requestMSH = new ArrayList<String>();
		List<String> responseMSH = new ArrayList<String>();
		String[] headerParts = text.split("\\|");
		for(int i=0; i< headerParts.length; i++) {
			requestMSH.add(headerParts[i]);
			responseMSH.add(headerParts[i]);
		}
		responseMSH.set(3, "Shaila Associates");
		System.out.println(requestMSH+"\n"+responseMSH);
	}
}

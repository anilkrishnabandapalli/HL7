package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Hl7Application {

	public static void main(String[] args) {
		SpringApplication.run(Hl7Application.class, args);
	}
	
	public void comment() throws IOException, ParseException{ //main(String[] args) throws IOException, ParseException {
		
		Map<String, String> messageMap = new HashMap<String, String>();
		Map<String, String> patientMap = new HashMap<String, String>();
		
		//SpringApplication.run(Hl7Application.class, args);
		BufferedReader br = new BufferedReader(new FileReader("/Users/anilbandapalli/DEVHOME/Anil/Files/OB-Case 1- 1st TR.txt"));
		//BufferedReader br = new BufferedReader(new FileReader("/Users/anilbandapalli/DEVHOME/Anil/Files/CH-car-P-Song-080817.doc"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		    	String key = line.substring(0, line.indexOf(' '));
		    	String value = line.substring(line.indexOf(' ') + 1);
		    	value = value.replace("\"", "");
		    	value = value.replace(" ", "^");
		    	patientMap.put(key, value);
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		generateHL7MessadeHeadermessageMap();
		generateHL7Patient(patientMap);
		generateHL7PatientVisit(patientMap);
		generateHL7ORCOrderSegment(patientMap);
		generateHL7ObsReqSeg(patientMap);
		generateHL7ObxResSeg(patientMap);
		generateHL7notes(patientMap);
		
	}
	
	public static void generateHL7notes(Map<String, String> patientMap) {
		System.out.println("Notes and Comments");
		if(patientMap.get("Notes") != null) {
			System.out.println("NTE"+"|"+"NT1"+"|"+"|"+patientMap.get("Notes"));
		} else {
			System.out.println("NTE"+"|"+"NT1"+"|"+"|"+"Notes are Given here for Patient observation during test");
		}
		
	}

	public static void generateHL7ObxResSeg(Map<String, String> patientMap) {
		System.out.println("Observation/Result Segment");
		
		System.out.println("OBX"+"|"+"OBX1"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+
					"|"+"|"+"|"+"|");
		
	}

	public static void generateHL7ObsReqSeg(Map<String, String> patientMap) {
		System.out.println("Observation Request Segment--Will be getting this from E-Clinicals");
		
		System.out.println("OBR"+"|"+"OB1"+"|"+"");
		
	}

	public static void generateHL7ORCOrderSegment(Map<String, String> patientMap) throws ParseException {
		System.out.println("Common Order Segment:");
		
		//System.out.println("ORC"+"|"+"|"+"OrdNum"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+extractDate(patientMap.get("CLINICAL_EDC"))+"|"+"|"+patientMap.get("")+"|"+"|"+"OrdProvider");
		
		if(patientMap.get("CLINICAL_EDC") != null)
			System.out.println("ORC"+"|"+"|"+"OrdNum"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+extractDate(patientMap.get("CLINICAL_EDC"))+"|"+"|"+"|"+"|"+"OrdProvider");
		else
			System.out.println("ORC"+"|"+"|"+"OrdNum"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"OrdProvider");
	}

	public static void generateHL7PatientVisit(Map<String, String> patientMap) {
		
		System.out.println("Patient Visit Info:");
		
		System.out.println("PV1"+"|"+"1"+"|"+"|"+"|"+"|"+"|"+"|"+patientMap.get("PERFORMING_PH")+"|"+patientMap.get("REFERRING_MD")+"|"+"Visit1");
	}

	public static void generateHL7Patient(Map<String, String> patientMap) throws ParseException{
		
		System.out.println("patient info with pipes");
		
		//Gender Definition
			if(patientMap.get("GENDER") != null) {
				if(patientMap.get("GENDER").equalsIgnoreCase("1")) {
				patientMap.put("GENDER","M");
				}
				else if(patientMap.get("GENDER").equalsIgnoreCase("0")) {
				patientMap.put("GENDER","F");
			}
			}
		else {
			patientMap.put("GENDER","U");
		}
		
		String newDateString = extractDate(patientMap.get("BIRTHDATE"));
		
		System.out.println("PID"+"|"+"1"+"|"+patientMap.get("PATID")+"|"+"|"+"|"+patientMap.get("PATNAME")+"|"+"|"+newDateString+"|"+patientMap.get("GENDER")+
				"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|");
	}

	public static String extractDate(String tempDate) throws ParseException {
		String newDateString = null;
		if(tempDate != null) {
			final String OLD_FORMAT = "dd.MM.yyyy";
			final String NEW_FORMAT = "YYYYMMdd";
			
			String oldDateString = tempDate;
			
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(oldDateString);
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);
			
		}
		return newDateString;
	}
	
	public static void generateHL7MessadeHeadermessageMap() {
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		Date d = new Date();
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
		System.out.println("Message Header with pipes");
		
		System.out.println("MSH"+"|"+"^"+"~"+"\\"+"&"+"|"+"ECW"+"|"+"Shaila"+"|"+"RAP1100"+"|"+"BN"+"|"+dateFormat.format(d)+
				"|"+"|"+"ORU^R01"+"|"+"UID"+"|"+"P"+"|"+"1.0");
		}
	
	
}

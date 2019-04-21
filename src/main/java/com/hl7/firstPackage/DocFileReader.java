package com.hl7.firstPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.commons.lang.StringUtils;

public class DocFileReader {

	public static void main(String[] args) {
		File file = null;
        WordExtractor extractor = null;
        Map<String, String> dataMap = new HashMap<String, String>();
        try
        {
        	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        String s = bufferRead.readLine();
	        System.out.println(s);

            file = new File("/Users/anilbandapalli/DEVHOME/Anil/Files/123.doc");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++)
            {
            	String dataRead = fileData[i].toString();
                if (dataRead.toString() != null)
                	if(!dataRead.trim().isEmpty() && !dataRead.trim().equals("") && !dataRead.trim().equals(" "))
                	{
                		
                		if(dataRead.contains("ULTRASOUND REPORT:")){
                			dataMap.put("ULTRASOUND REPORT", dataRead.split(":")[1].toString().trim());
                		}
                		if(dataRead.contains("ID:")){
                			dataMap.put("PATID", StringUtils.substringBetween(dataRead, "ID:", "DOS").trim());
                		}
                		if(dataRead.contains("Name:")){
                			dataMap.put("PATNAME", StringUtils.substringBetween(dataRead, "Name:", "DOB").trim());
                		}
                		if(dataRead.contains("REFERRING PHYSICIAN:")){
                			dataMap.put("REFERRING_MD", dataRead.substring(dataRead.lastIndexOf("REFERRING PHYSICIAN: ")).split(":")[1].trim());
                		}
                		if(dataRead.contains("DOB:")){
                			dataMap.put("BIRTHDATE", StringUtils.substringBetween(dataRead, "DOB:", "Age").trim());
                		}
                		if(dataRead.contains("Age:")){
                			if(StringUtils.substringBetween(dataRead, "Age:", "ID") != null)
                				dataMap.put("AGE", StringUtils.substringBetween(dataRead, "Age:", "ID").trim());
                			else
                				dataMap.put("AGE", dataRead.substring(dataRead.lastIndexOf("Age:")).split(":")[1].trim());
                		}
                		if(dataRead.contains("DOS:")){
                			dataMap.put("DOS", dataRead.substring(dataRead.lastIndexOf("DOS:")).split(":")[1].trim());
                		}
                		if(dataRead.contains("Indication:")){
                			dataMap.put("Indication", dataRead.substring(dataRead.lastIndexOf("Indication:")).split(":")[1].trim());
                			String notes= "";
                			
                			for(int j=i+1;!fileData[j].contains("Impression:"); j++,i++) {
                				if(fileData[j] != null ){
                					
                				notes = notes + fileData[j].trim();
                				}
                			}
                			dataMap.put("Notes", notes);
                			System.out.println(notes);
                		}
                		if(dataRead.contains("Impression:")){
                			i++;
                			dataRead = fileData[i].trim();
                			dataMap.put("Impression", dataRead.trim());
                			
                		}
                	}
            }
            
            for(String data : dataMap.keySet())
            {
            	String value = dataMap.get(data);
            	value = value.replace("\"", "");
		    	value = value.replace(" ", "^");
		    	
            	dataMap.put(data, value);
            }
            System.out.println("MAP:"+dataMap);
            ///Hl7Application hl7 = new Hl7Application();
            generateHL7MessadeHeadermessageMap();
            generateHL7Patient(dataMap);
           generateHL7PatientVisit(dataMap);
            generateHL7ORCOrderSegment(dataMap);
            generateHL7ObsReqSeg(dataMap);
            generateHL7ObxResSeg(dataMap);
            generateHL7notes(dataMap);
        }
        
        catch (Exception exep)
        {
            exep.printStackTrace();
        }

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
				"|"+"|"+"ORU^R01"+"|"+""+"|"+"T"+"|"+"1.0");
		}
	
	


	public static String extractDate(String tempDate) throws ParseException {
		String newDateString = null;
		if(tempDate != null) {
			final String OLD_FORMAT = "dd/MM/yyyy";
			final String NEW_FORMAT = "YYYYMMdd";
			
			String oldDateString = tempDate;
			
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(oldDateString);
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);
			
		}
		return newDateString;
	}

}

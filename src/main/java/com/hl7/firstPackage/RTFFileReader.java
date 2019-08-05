package com.hl7.firstPackage;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hl7.*;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class RTFFileReader {

	public static void main(String[] args) {
		File file = null;
        WordExtractor extractor = null;
        Map<String, String> dataMap = new HashMap<String, String>();
        try
        {

            file = new File("/Users/anilbandapalli/DEVHOME/Anil/Files/GR-dlec-M-Diaz-080917.doc");
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
                			dataMap.put("ID", StringUtils.substringBetween(dataRead, "ID:", "DOS").trim());
                		}
                		if(dataRead.contains("Name:")){
                			dataMap.put("Name", StringUtils.substringBetween(dataRead, "Name:", "DOB").trim());
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
            Hl7Application hl7 = new Hl7Application();
            hl7.generateHL7MessadeHeadermessageMap();
            generateHL7Patient(dataMap);
            hl7.generateHL7PatientVisit(dataMap);
            hl7.generateHL7ORCOrderSegment(dataMap);
            hl7.generateHL7ObsReqSeg(dataMap);
            hl7.generateHL7ObxResSeg(dataMap);
            hl7.generateHL7notes(dataMap);
        }
        
        catch (Exception exep)
        {
            exep.printStackTrace();
        }

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

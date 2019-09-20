package com.hl7.firstPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertHL7String {

	public static void main(String[] args) {

		String HL7RawMessage = "MSH|^~\\&|ECW|LFMC|||20190408145903||ORM^O01|31320190408145903|P|2.3|\r\n"+
		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||\r\n"+
		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||137042|\r\n"+
		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|\r\n"+
		"ORC|NW|137042|||||||20190408145903|||1326018813^GRIEVESON^JOHN^|\r\n"+
		"OBR|1|137042||SA_004^US abdomen Limited e-sch|||20190408145800|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|\r\n"+
		"DG1|1|I10|G91.2|(Idiopathic) normal pressure hydrocephalus|\r\n"+
		"OBX|1|ST|Lab022^4 hour fasting prior to exam||yes|\r\n"+
		"OBX|2|ST|Lab023^8 hour fasting prior to exam||Yes|\r\n"+
		"OBX|3|ST|Lab024^Fasting is required||Yes|\r\n"+
		"OBX|4|ST|Lab025^Fasting is Not Required||no|\r\n"+
		"OBX|5|ST|Lab026^Full Bladder is required||Yes|\r\n"+
		"OBX|6|ST|Lab027^Full Bladder is not required||NO|\r\n"+
		"OBX|7|ST|Lab028^No preparation required for exam||Yes|";
		
//		List<String> HL7Strings = Arrays.asList(HL7RawMessage.split("\r\n"));
//		System.out.println(HL7Strings);
//		extraxtMSH(HL7Strings);List<String> HL7Strings = Arrays.asList(HL7RawMessage.split("\\|"));
		List<String> HL7Strings = Arrays.asList(HL7RawMessage.split("\\|"));
		extraxtMSH(HL7Strings, "MSH", 12);
	}

	public static List<String> extraxtMSH(List<String> HL7Strings, String headerString, int lengthOfSeg) {
		List<String> mshStrings = new ArrayList<String>();
		int mshIndex = HL7Strings.indexOf(headerString);
		if(mshIndex == -1) {
			mshIndex = HL7Strings.indexOf("\r"+headerString);
			//mshIndex = HL7Strings.matches(" PID");
			//HL7Strings.get(12);
		}
		if(mshIndex != -1) {
		for(int i= mshIndex; i < mshIndex+lengthOfSeg; i++) {
			mshStrings.add(HL7Strings.get(i));
			
			System.out.println("Index"+i+": "+HL7Strings.get(i));
		}
		}
		return mshStrings;
		
	}
	
	
//	public static List<String> extraxtMSH(List<String> HL7Strings) {
//		List<String> headerString=Arrays.asList(HL7Strings.get(0).split("\\|"));
//		List<String> mshStrings = new ArrayList<String>();
//	
//		System.out.println(headerString);
//		return mshStrings;
//		
//	}

}

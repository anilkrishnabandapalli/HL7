package com.hl7.firstPackage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.datatype.CE;
import ca.uhn.hl7v2.model.v24.datatype.ED;
import ca.uhn.hl7v2.model.v24.datatype.PL;
import ca.uhn.hl7v2.model.v24.datatype.XAD;
import ca.uhn.hl7v2.model.v24.datatype.XCN;
import ca.uhn.hl7v2.model.v24.datatype.XPN;
import ca.uhn.hl7v2.model.v24.datatype.XTN;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.NTE;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.ORC;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;

public class OurOruR01MessageBuilder {
    private ORU_R01 _oruR01Message;
    //private OurBase64Helper _ourBase64Helper = new OurBase64Helper();

    /*
    * You can pass in a domain or data transfer object as a parameter when
    * integrating with data from your application here I will leave that to you to
    * explore on your own Using fictional data here for illustration
    */

    public ORU_R01 Build(String hl7Message, String observation) throws HL7Exception, IOException {
        String currentDateTimeString = getCurrentTimeStamp();
        _oruR01Message = new ORU_R01();
        // you can use the context class's newMessage method to instantiate a message if
        // you want
        _oruR01Message.initQuickstart("ORU", "R01", "P");
        
//        String HL7RawMessage = "MSH|^~\\&|ECW|LFMC|||20190408145903||ORM^O01|31320190408145903|P|2.3|"+
//        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||"+
//        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||137042|"+
//        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|"+
//        		"ORC|NW|137042|||||||20190408145903|||1326018813^GRIEVESON^JOHN^|"+
//        		"OBR|1|137042||SA_004^US abdomen Limited e-sch|||20190408145800|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|"+
//        		"DG1|1|I10|G91.2|(Idiopathic) normal pressure hydrocephalus|"+
//        		"OBX|1|ST|Lab022^4 hour fasting prior to exam||yes|"+
//        		"OBX|2|ST|Lab023^8 hour fasting prior to exam||Yes|"+
//        		"OBX|3|ST|Lab024^Fasting is required||Yes|"+
//        		"OBX|4|ST|Lab025^Fasting is Not Required||no|"+
//        		"OBX|5|ST|Lab026^Full Bladder is required||Yes|"+
//        		"OBX|6|ST|Lab027^Full Bladder is not required||NO|"+
//        		"OBX|7|ST|Lab028^No preparation required for exam||Yes|";
//        
//        HL7RawMessage = "MSH|^~\\&|ECW|LFMC|||20190521123005||ORM^O01|32220190521123005|P|2.3|" + 
//        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||" + 
//        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||140334|" + 
//        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|" + 
//        		"ORC|NW|140334|||||||20190521123005|||1326018813^GRIEVESON^JOHN^|" + 
//        		"OBR|1|140334||SA_004^US abdomen Limited e-sch|||20190521112900|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|" + 
//        		"DG1|1|I10|A42.1|Abdominal actinomycosis|" + 
//        		"OBX|1|ST|Lab022^4 hour fasting prior to exam||NA|" + 
//        		"OBX|2|ST|Lab023^8 hour fasting prior to exam||Yes|" + 
//        		"OBX|3|ST|Lab024^Fasting is required||Yes|" + 
//        		"OBX|4|ST|Lab025^Fasting is Not Required||NA|" + 
//        		"OBX|5|ST|Lab026^Full Bladder is required||Yes|" + 
//        		"OBX|6|ST|Lab027^Full Bladder is not required||NA|" + 
//        		"OBX|7|ST|Lab028^No preparation required for exam||Yes| " + 
//        		"";
        
//        HL7RawMessage = "MSH|^~\\&|ECW|LFMC|||20190521123238||ORM^O01|32320190521123238|P|2.3|" + 
//        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||" + 
//        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||140335|" + 
//        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|" + 
//        		"ORC|NW|140335|||||||20190521123238|||1326018813^GRIEVESON^JOHN^|" + 
//        		"OBR|1|140335||SA_011^US Chest|||20190521113200|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|" + 
//        		"DG1|1|I10|I71.4|Abdominal aortic aneurysm (AAA) without rupture|" + 
//        		"OBX|1|ST|Lab071^4 hour fasting prior to exam||NA|" + 
//        		"OBX|2|ST|Lab072^8 hour fasting prior to exam||Yes|" + 
//        		"OBX|3|ST|Lab073^Fasting is required||Yes|" + 
//        		"OBX|4|ST|Lab074^Fasting is Not Required||NA|" + 
//        		"OBX|5|ST|Lab075^Full Bladder is required||Yes|";
        
		/*
		 * String HL7RawMessage =
		 * "MSH|^~\\&|ECW|LFMC|||20190529120904||ORM^O01|32820190529120904|P|2.3|" +
		 * "PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||"
		 * + "PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||140928|" +
		 * "GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|"
		 * + "ORC|NW|140928|||||||20190529120904|||1326018813^GRIEVESON^JOHN^|" +
		 * "OBR|1|140928||SA_004^US abdomen Limited e-sch|||20190529120800|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|"
		 * + "DG1|1|I10|G91.2|(Idiopathic) normal pressure hydrocephalus|" +
		 * "OBX|1|ST|Lab022^4 hour fasting prior to exam||NA|" +
		 * "OBX|2|ST|Lab023^8 hour fasting prior to exam||Yes|" +
		 * "OBX|3|ST|Lab024^Fasting is required||Yes|" +
		 * "OBX|4|ST|Lab025^Fasting is Not Required||NA|" +
		 * "OBX|5|ST|Lab026^Full Bladder is required||No|";
		 */
        String HL7RawMessage= "MSH|^~\\&|ECW|LFMC|||20190722154048||ORM^O01|36320190722154048|P|2.3|\n" + 
        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||\n" + 
        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||145494|\n" + 
        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|\n" + 
        		"ORC|NW|145494|||||||20190722154048|||1326018813^GRIEVESON^JOHN^|\n" + 
        		"OBR|1|145494||SA_005^US Aorta e-sch|||20190722154000|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|\n" + 
        		"DG1|1|I10|I35.0|Nonrheumatic aortic (valve) stenosis|\n" + 
        		"OBX|1|ST|Lab029^4 hour fasting prior to exam||NA|\n" + 
        		"OBX|2|ST|Lab030^8 hour fasting prior to exam||Yes|\n" + 
        		"OBX|3|ST|Lab031^Fasting is required||Yes|\n" + 
        		"OBX|4|ST|Lab032^Fasting is Not Required||No|\n" + 
        		"OBX|5|ST|Lab033^Full Bladder is required||Yes|\n" + 
        		"";
        
        HL7RawMessage="MSH|^~\\&|ECW|LFMC|||20190816114203||ORM^O01|37520190816114203|P|2.3|\n" + 
        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||\n" + 
        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||147372|\n" + 
        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|\n" + 
        		"ORC|NW|147372|||||||20190816114203|||1326018813^GRIEVESON^JOHN^|\n" + 
        		"OBR|1|147372||SA_008^US Carotid Duplex Bilateral e-sch|||20190816114100|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|\n" + 
        		"DG1|1|I10|I70.0|Atherosclerosis of aorta|\n" + 
        		"OBX|1|ST|Lab050^4 hour fasting prior to exam||NA|\n" + 
        		"OBX|2|ST|Lab051^8 hour fasting prior to exam||Yes|\n" + 
        		"OBX|3|ST|Lab052^Fasting is required||Yes|\n" + 
        		"OBX|4|ST|Lab053^Fasting is Not Required||NA|\n" + 
        		"OBX|5|ST|Lab054^Full Bladder is required||Yes|\n"
        		;
        
        HL7RawMessage="MSH|^~\\&|ECW|LFMC|||20190816114953||ORM^O01|37620190816114953|P|2.3|\n" + 
        		"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC||\n" + 
        		"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||147373|\n" + 
        		"GT1|1||TEST^Test^||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130|5084445555||19900101|Male||1|\n" + 
        		"ORC|NW|147373|||||||20190816114953|||1326018813^GRIEVESON^JOHN^|\n" + 
        		"OBR|1|147373||SA_014^US Head /Neck Soft Tissue e-sch|||20190816114900|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|\n" + 
        		"DG1|1|I10|G91.2|(Idiopathic) normal pressure hydrocephalus|\n" + 
        		"OBX|1|ST|Lab092^4 hour fasting prior to exam||NA|\n" + 
        		"OBX|2|ST|Lab093^8 hour fasting prior to exam||Yes|\n" + 
        		"OBX|3|ST|Lab094^Fasting is required||Yes|\n" + 
        		"OBX|4|ST|Lab095^Fasting is Not Required||NA|\n" + 
        		"OBX|5|ST|Lab096^Full Bladder is required||Yes|\n" + 
        		"";
        		List<String> HL7Strings = Arrays.asList(hl7Message.split("\\|"));
        		observation=observation.replace("\r\n", "\n");
        		observation=observation.replace("\n", "~");

        ConvertHL7String hl7String = new ConvertHL7String();
        CreateMshSegment(currentDateTimeString, hl7String.extraxtMSH(HL7Strings, "MSH", 12));
        CreatePidSegment(hl7String.extraxtMSH(HL7Strings, "PID", 20));
        CreatePv1Segment(hl7String.extraxtMSH(HL7Strings, "PV1", 9));
        CreateORCSegment(hl7String.extraxtMSH(HL7Strings, "ORC", 13));
        CreateObrSegment(hl7String.extraxtMSH(HL7Strings, "OBR", 28));
        CreateObxSegment(hl7String.extraxtMSH(HL7Strings, "OBX", 16), observation);
        CreateNTESegment(hl7String.extraxtMSH(HL7Strings, "NTE", 0), observation);
        return _oruR01Message;
    }

    private void CreateNTESegment(List<String> extraxtNTE, String observation ) throws DataTypeException  {
		NTE nte = _oruR01Message.getPATIENT_RESULT().getPATIENT().getNTE();
		
		nte.getComment(0).setValue(observation);
	}

	private void CreateORCSegment(List<String> extraxtORC) throws DataTypeException {
		ORC orcSegment = _oruR01Message.getPATIENT_RESULT().getORDER_OBSERVATION().getORC();
		orcSegment.getOrderControl().setValue(extraxtORC.get(1));
		orcSegment.getPlacerOrderNumber().getUniversalID().setValue(extraxtORC.get(2));
		orcSegment.getFillerOrderNumber().getUniversalID().setValue(extraxtORC.get(3));
		orcSegment.getPlacerGroupNumber().getUniversalID().setValue(extraxtORC.get(4));
		orcSegment.getOrderStatus().setValue(extraxtORC.get(5));
		orcSegment.getResponseFlag().setValue(extraxtORC.get(6));
		orcSegment.getDateTimeOfTransaction().getTimeOfAnEvent().setValue(extraxtORC.get(9));
		orcSegment.getOrderingProvider(0).getFamilyName().getSurname().setValue(extraxtORC.get(12).split("\\^")[1]);
		orcSegment.getOrderingProvider(0).getGivenName().setValue(extraxtORC.get(12).split("\\^")[2]);// + " "+ extraxtORC.get(12).split("\\^")[3]);
		orcSegment.getOrderingProvider(0).getIdentifierTypeCode().setValue(extraxtORC.get(12).split("\\^")[0]);
	}

	private void CreateMshSegment(String currentDateTimeString, List<String> mshHL7Strings) throws DataTypeException {
        MSH mshSegment = _oruR01Message.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getSendingApplication().getNamespaceID().setValue("RAD");
        mshSegment.getSendingFacility().getNamespaceID().setValue("LFMC");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("RAD");
        mshSegment.getReceivingFacility().getNamespaceID().setValue("LFMC");
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(currentDateTimeString);
        mshSegment.getMessageControlID().setValue(getSequenceNumber());
        mshSegment.getProcessingID().getProcessingID().setValue("P");
        mshSegment.getVersionID().getVersionID().setValue("2.3");
    }

    private void CreatePidSegment(List<String> pidHL7Strings) throws DataTypeException {
        PID pid = _oruR01Message.getPATIENT_RESULT().getPATIENT().getPID();
        XPN patientName = pid.getPatientName(0);

        patientName.getFamilyName().getSurname().setValue(pidHL7Strings.get(5).split("\\^")[0]);
        String pName = pidHL7Strings.get(5).split("\\^")[1];
        if(pidHL7Strings.get(5).split("\\^").length == 3){
        	pName = pName + pidHL7Strings.get(5).split("\\^")[2];
        }
        patientName.getGivenName().setValue(pName);
        pid.getPatientIdentifierList(0).getID().setValue(pidHL7Strings.get(2));
        XAD patientAddress = pid.getPatientAddress(0);
        patientAddress.getStreetAddress().getStreetOrMailingAddress().setValue(
        		pidHL7Strings.get(11).split("\\^")[0]+" "+pidHL7Strings.get(11).split("\\^")[1]);
        patientAddress.getCity().setValue(pidHL7Strings.get(11).split("\\^")[2]);
        patientAddress.getStateOrProvince().setValue(pidHL7Strings.get(11).split("\\^")[3]);
        patientAddress.getZipOrPostalCode().setValue(pidHL7Strings.get(11).split("\\^")[4]);
        patientAddress.getCountry().setValue(pidHL7Strings.get(12));
        
        XTN phoneNumberHome = pid.getPhoneNumberHome(0);
        phoneNumberHome.getPhoneNumber().setValue(pidHL7Strings.get(13));
        
        //XTN phoneNumberBusiness = pid.getPhoneNumberBusiness(0);
        //phoneNumberBusiness.getPhoneNumber().setValue(pidHL7Strings.get(14));
        
        CE primaryLang = pid.getPrimaryLanguage();
        primaryLang.getIdentifier().setValue(pidHL7Strings.get(15));
        
       // pid.getMaritalStatus().getText().setValue(pidHL7Strings.get(16).split("\\^")[0]);
        
        pid.getPatientAccountNumber().getID().setValue(pidHL7Strings.get(18));
        
        pid.getSSNNumberPatient().setValue(pidHL7Strings.get(19));
        
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(pidHL7Strings.get(7));
    }

    private void CreatePv1Segment(List<String> pv1HL7Strings) throws DataTypeException {
        PV1 pv1 = _oruR01Message.getPATIENT_RESULT().getPATIENT().getVISIT().getPV1();
        pv1.getPatientClass().setValue("O"); // to represent an 'Outpatient'
        //PL assignedPatientLocation = pv1.getAssignedPatientLocation();
        //assignedPatientLocation.getFacility().getNamespaceID().setValue("Some Treatment Facility Name");
        //assignedPatientLocation.getPointOfCare().setValue("Some Point of Care");
        //pv1.getAdmissionType().setValue("ALERT"); AB Not Applicable
        
        pv1.getVisitNumber().getID().setValue(pv1HL7Strings.get(1));;
        
        XCN attendingDoctor = pv1.getAttendingDoctor(0);
        //attendingDoctor.getIDNumber().setValue("99999999");
        attendingDoctor.getFamilyName().getSurname().setValue(pv1HL7Strings.get(7).split("\\^")[1]);
        attendingDoctor.getGivenName().setValue(pv1HL7Strings.get(7).split("\\^")[2]);// + " "+ pv1HL7Strings.get(7).split("\\^")[3]);
        attendingDoctor.getIdentifierTypeCode().setValue(pv1HL7Strings.get(7).split("\\^")[0]);
        
        XCN referringDoctor = pv1.getReferringDoctor(0);
        //referringDoctor.getIDNumber().setValue("99999999");
        //referringDoctor.getFamilyName().getSurname().setValue(pv1HL7Strings.get(8).split("\\^")[1]);
        //referringDoctor.getGivenName().setValue(pv1HL7Strings.get(8).split("\\^")[2]);// + " "+ pv1HL7Strings.get(8).split("\\^")[3]);
        //referringDoctor.getIdentifierTypeCode().setValue(pv1HL7Strings.get(8).split("\\^")[0]);
        
        pv1.getAdmitDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        
        //pv1.getVisitNumber().getID().setValue(pv1HL7Strings.get(9));
    }

    private void CreateObrSegment(List<String> obxHL7Strings) throws DataTypeException {
        ORU_R01_ORDER_OBSERVATION orderObservation = _oruR01Message.getPATIENT_RESULT().getORDER_OBSERVATION();
        OBR obr = orderObservation.getOBR();
        obr.getSetIDOBR().setValue("1");
        obr.getPlacerOrderNumber().getUniversalID().setValue(obxHL7Strings.get(2));
        obr.getFillerOrderNumber().getUniversalID().setValue(obxHL7Strings.get(3));
        //obr.getFillerOrderNumber().getName().S
        obr.getFillerOrderNumber().getEntityIdentifier().setValue("4.0"+"");
        obr.getUniversalServiceIdentifier().getIdentifier().setValue(obxHL7Strings.get(4).split("\\^")[0]);
        obr.getUniversalServiceIdentifier().getText().setValue(obxHL7Strings.get(4).split("\\^")[1]);
        //obr.getObservationEndDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        obr.getObservationDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        obr.getSpecimenActionCode().setValue(obxHL7Strings.get(4).split("\\^")[0]+" "+obxHL7Strings.get(4).split("\\^")[1]);
        obr.getResultStatus().setValue("F");
        obr.getPriority().setValue(obxHL7Strings.get(5));
        obr.getSpecimenReceivedDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(obxHL7Strings.get(16).split("\\^")[1]);
        obr.getOrderingProvider(0).getGivenName().setValue(obxHL7Strings.get(16).split("\\^")[2]);//+" "+ obxHL7Strings.get(16).split("\\^")[3]);
        

    }

    private void CreateObxSegment(List<String> obxHL7Strings, String observation1) throws DataTypeException, IOException {
    	//if(obxHL7Strings != null && obxHL7Strings.size() > 0) {
        ORU_R01_OBSERVATION observation = _oruR01Message.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATION(0);
        OBX obx = observation.getOBX();
        obx.getSetIDOBX().setValue("0");
        obx.getValueType().setValue("ST");
        obx.getObservationIdentifier().getIdentifier().setValue("SA_011 US Chest");
        Varies value = obx.getObservationValue(0);
        ED encapsulatedData = new ED(_oruR01Message);
        //String base64EncodedStringOfPdfReport = _ourBase64Helper.ConvertToBase64String(new File("C:\\HL7TestInputFiles\\Sample Pathology Lab Report.pdf"));
        encapsulatedData.getEd1_SourceApplication().getHd1_NamespaceID().setValue("Shaila");
        //encapsulatedData.getTypeOfData().setValue("AP"); //see HL7 table 0191: Type of referenced data
        //encapsulatedData.getDataSubtype().setValue("PDF");
        //encapsulatedData.getEncoding().setValue("Base64");
        
        //encapsulatedData.getData().setValue(base64EncodedStringOfPdfReport);
        encapsulatedData.getData().setValue(observation1);
        value.setData(encapsulatedData);
    	//}
    }

    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private String getSequenceNumber() {
        String facilityNumberPrefix = "AB"; // some arbitrary prefix for the facility
        return facilityNumberPrefix.concat(getCurrentTimeStamp());
    }
}
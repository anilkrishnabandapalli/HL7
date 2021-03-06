package com.hl7.firstPackage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.PL;
import ca.uhn.hl7v2.model.v24.datatype.XAD;
import ca.uhn.hl7v2.model.v24.datatype.XCN;
import ca.uhn.hl7v2.model.v24.datatype.XPN;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.segment.EVN;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;

public class OurAdtA01MessageBuilder {
	private ADT_A01 _adtMessage;

    /*You can pass in a domain object as a parameter
    when integrating with data from your application here
    I will leave that to you to explore on your own
    Using fictional data here for illustration*/

    public ADT_A01 Build(String hL7Message) throws HL7Exception, IOException {
        String currentDateTimeString = getCurrentTimeStamp();
        _adtMessage = new ADT_A01(); 
        //you can use the context class's newMessage method to instantiate a message if you want
        
        _adtMessage.initQuickstart("ADT", "A01", "P");
         
        createMshSegment(currentDateTimeString, hL7Message);
        createEvnSegment(currentDateTimeString, hL7Message);
        createPidSegment(hL7Message);
        createPv1Segment(hL7Message);
        return _adtMessage;
    }

    private void createMshSegment(String currentDateTimeString, String hL7Message) throws DataTypeException {
        MSH mshSegment = _adtMessage.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getSendingApplication().getNamespaceID().setValue("ECW");
        mshSegment.getSendingFacility().getNamespaceID().setValue("Shaila");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("ECW");
        mshSegment.getReceivingFacility().getNamespaceID().setValue("LFMC");
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(currentDateTimeString);
        mshSegment.getMessageControlID().setValue(getSequenceNumber());
        mshSegment.getVersionID().getVersionID().setValue("2.1");
    }

    private void createEvnSegment(String currentDateTimeString, String hL7Message) throws DataTypeException {
        EVN evn = _adtMessage.getEVN();
        evn.getEventTypeCode().setValue("A01");
        evn.getRecordedDateTime().getTimeOfAnEvent().setValue(currentDateTimeString);
    }

    private void createPidSegment(String hL7Message) throws DataTypeException {
        PID pid = _adtMessage.getPID();
        XPN patientName = pid.getPatientName(0);
        patientName.getFamilyName().getSurname().setValue("Test");
        patientName.getGivenName().setValue("Test");
        pid.getPatientIdentifierList(0).getID().setValue("AB13330");
        XAD patientAddress = pid.getPatientAddress(0);
        patientAddress.getStreetAddress().getStreetOrMailingAddress().setValue("21 ANY STREET APARTMENT 300B");
        patientAddress.getCity().setValue("JAMAICA PLAIN");
        patientAddress.getStateOrProvince().setValue("MA");
        patientAddress.getZipOrPostalCode().setValue("02130");
        patientAddress.getCountry().setValue("USA");
    }

    private void createPv1Segment(String hL7Message) throws DataTypeException {
        PV1 pv1 = _adtMessage.getPV1();
        pv1.getPatientClass().setValue("O"); // to represent an 'Outpatient'
        PL assignedPatientLocation = pv1.getAssignedPatientLocation();
        assignedPatientLocation.getFacility().getNamespaceID().setValue("");
        assignedPatientLocation.getPointOfCare().setValue("");
        //pv1.getAdmissionType().setValue("ALERT");
        XCN referringDoctor = pv1.getReferringDoctor(0);
        referringDoctor.getIDNumber().setValue("1326018813");
        referringDoctor.getFamilyName().getSurname().setValue("GRIEVESON");
        referringDoctor.getGivenName().setValue("JOHN");
        referringDoctor.getIdentifierTypeCode().setValue("456789");
        pv1.getAdmitDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
    }
    
    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private String getSequenceNumber() {
        String facilityNumberPrefix = "1234"; // some arbitrary prefix for the facility
        return facilityNumberPrefix.concat(getCurrentTimeStamp());
    }
}

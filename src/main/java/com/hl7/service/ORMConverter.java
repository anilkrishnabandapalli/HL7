package com.hl7.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hl7.model.OBRInfo;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v22.segment.DG1;
import ca.uhn.hl7v2.model.v22.segment.OBR;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v22.message.ORM_O01;
import ca.uhn.hl7v2.model.v22.segment.PID;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ORMConverter {

	@Value("${application.savepath}")
	private String path;
	
	@Value("${application.mailto}")
	private String mailTo;

	@Autowired
	ReportGenerator reportGenerator;
	
	@Autowired
	EmailUtility emailUtility;
	
	private static Logger log = LoggerFactory.getLogger(ORMConverter.class);

	public void convertReceivedMessage(String receivedMessage) {

		String patientName, dob, sex, phone, address;
		String orderDate, today;

		ORM_O01 ormMessage = getORM(receivedMessage);

//		Patient Info
		patientName = getPatientName(ormMessage);
		dob = getDob(ormMessage);
		sex = getSex(ormMessage);
		address = getAddress(ormMessage);
		phone = getPhone(ormMessage);

//		Dates of transaction and today as per order format
		orderDate = getOrderDate(ormMessage);
		today = getToday();

//		Observations
		JRBeanCollectionDataSource patientOBR = getPatientOBR(ormMessage);

//		Generate PDF

		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("patientName", patientName);
		parameters.put("dob", dob);
		parameters.put("sex", sex);
		parameters.put("address", address);
		parameters.put("phone", phone);
		parameters.put("today", today);
		parameters.put("orderDate", orderDate);
		parameters.put("OBRDataset", patientOBR);

		String fileName = patientName.replace(' ', '_') + "_"
				+ new SimpleDateFormat("MMddyyyyHHmmss").format(new Date());
		String reportFileName = fileName + ".pdf";
		String messageFileName = fileName + ".txt";

		renameReceivedMessageFile(messageFileName);
		reportGenerator.generateAndSavePdf(reportFileName, parameters);

//		Send Mail
		String subject= "Lab Order for "+patientName.replace(' ', '_');
		emailUtility.sendMail(mailTo,subject," ", reportFileName);

		log.info("ORM Conversion Operation completed");
	}

	private void renameReceivedMessageFile(String messageFileName) {

		LocalDate date = LocalDate.now();
		String homeDir = System.getProperty("user.home");
		File file = new File(homeDir + "/" + path + "/" + date.toString() + "/test.txt");
		File newFile = new File(homeDir + "/" + path + "/" + date.toString()+"/"+messageFileName);

		if (file.renameTo(newFile)) {
			log.info("Successfully saved received message at {}", newFile);
		} else {
			log.error("Error saving received HL7 message with desired filename");
			log.error("Received message is currently saved in {}", file);
		}
	}

	private ORM_O01 getORM(String receivedMessage) {

		HapiContext context = new DefaultHapiContext();

		ORM_O01 ormMessage = new ORM_O01();
		ormMessage.setParser(context.getPipeParser());
		try {
			ormMessage.parse(receivedMessage);
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}

		return ormMessage;
	}

	private String getPatientName(ORM_O01 ormMessage) {
		PID patient = ormMessage.getPATIENT().getPID();
		String patientName = "N/A";
		try {
			patientName = patient.getPid5_PatientName().encode().replace("^", " ");
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return patientName;
	}

	private String getDob(ORM_O01 ormMessage) {
		PID patient = ormMessage.getPATIENT().getPID();
		SimpleDateFormat hl7Format = new SimpleDateFormat("yyyyMMdd");
		String dob = "N/A";
		try {
			Date oldFormatDate = hl7Format.parse(patient.getPid7_DateOfBirth().encode());
			dob = (new SimpleDateFormat("MM/dd/yyyy")).format(oldFormatDate);
		} catch (ParseException e) {
			log.error("Exception occured : ", e);
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return dob;

	}

	private String getSex(ORM_O01 ormMessage) {
		PID patient = ormMessage.getPATIENT().getPID();
		String sex = "N/A";
		try {
			sex = patient.getPid8_Sex().encode();
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return sex;
	}

	private String getAddress(ORM_O01 ormMessage) {
		PID patient = ormMessage.getPATIENT().getPID();
		String address = "N/A";
		try {
			if (patient.getPid11_PatientAddress(0).encode() != null
					&& !patient.getPid11_PatientAddress(0).encode().equals(""))
				address = patient.getPid11_PatientAddress(0).encode().replace("^", ", ");
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return address;
	}

	private String getPhone(ORM_O01 ormMessage) {
		PID patient = ormMessage.getPATIENT().getPID();
		String phone = "N/A";
		try {
			if (patient.getPid13_PhoneNumberHome(0).encode() != null
					&& !patient.getPid13_PhoneNumberHome(0).encode().equals(""))
				phone = patient.getPid13_PhoneNumberHome(0).encode();
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return phone;
	}

	private String getOrderDate(ORM_O01 ormMessage) {
		String orderDate = "N/A";
		SimpleDateFormat orderFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			Date hl7OrderDate = (new SimpleDateFormat("yyyyMMddHHmmss"))
					.parse(ormMessage.getORDER().getORC().getOrc9_DateTimeOfTransaction().encode());
			orderDate = orderFormat.format(hl7OrderDate);
		} catch (ParseException e) {
			log.error("Error occured : {}", e);
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return orderDate;
	}

	private String getToday() {
		SimpleDateFormat orderFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String today = orderFormat.format(new Date());
		return today;
	}

	private JRBeanCollectionDataSource getPatientOBR(ORM_O01 ormMessage) {

		List<OBRInfo> obrInfoList = new ArrayList<OBRInfo>();

		try {
			String[] orderNames = ormMessage.getORDER().getORDER_DETAIL().getNames();
			for (int index = 0; index < orderNames.length; index++) {
				if (orderNames[index].startsWith("OBR")) {
					Structure[] obrRecords = ormMessage.getORDER().getORDER_DETAIL().getAll(orderNames[index]);
					for (Structure patient_obr : obrRecords) {
						OBRInfo obrInfo = getObr(patient_obr, ormMessage, index, orderNames);
						obrInfoList.add(obrInfo);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception ", e);
		}

//		OBR patient_obr = ormMessage.getORDER().getORDER_DETAIL().getOBR();
//		List<OBRInfo> obrInfoList = new ArrayList<OBRInfo>();
//		OBRInfo obrInfo = getObr(patient_obr,ormMessage);
//
//		obrInfoList.add(obrInfo);
//		
//		try {
//			Structure[] patient_obrs2= ormMessage.getORDER().getORDER_DETAIL().getAll("OBR2");
//			for(Structure patient_obr2:patient_obrs2) {
//				OBRInfo obrInfo2=getObr((OBR)patient_obr2,ormMessage);
//				obrInfoList.add(obrInfo2);
//			}
//			
//			log.error("Exception occured : ",e);
//		}

		JRBeanCollectionDataSource patientOBR = new JRBeanCollectionDataSource(obrInfoList);

		return patientOBR;
	}

	private OBRInfo getObr(Structure patient_obr, ORM_O01 ormMessage, int index, String[] orderNames) {
		OBRInfo obrInfo = new OBRInfo();
		obrInfo.setCode(getObrCode((OBR) patient_obr));
		obrInfo.setDiagnosticName(getObrDiagnosticName((OBR) patient_obr));
		obrInfo.setFasting(getObrPriority((OBR) patient_obr));
		obrInfo.setPriority(getObrFasting((OBR) patient_obr));

		String assessment = "N/A";
		for (int index2 = index + 1; index2 < orderNames.length; index2++) {
			if (orderNames[index2].startsWith("OBR"))
				break;
			if (orderNames[index2].startsWith("DG1")) {
				assessment = getObrAssessment(ormMessage, orderNames[index2]);
				break;
			}
		}

		obrInfo.setAssessment(assessment);
		return obrInfo;

	}

	/*
	 * private OBRInfo getObr(OBR patient_obr, ORM_O01 ormMessage) { String code,
	 * diagnosticName, fasting, priority, assessment; code =
	 * getObrCode(patient_obr); diagnosticName = getObrDiagnosticName(patient_obr);
	 * priority = getObrPriority(patient_obr); fasting = getObrFasting(patient_obr);
	 * assessment = getObrAssessment(ormMessage);
	 * 
	 * OBRInfo obrInfo = new OBRInfo(); obrInfo.setCode(code);
	 * obrInfo.setDiagnosticName(diagnosticName); obrInfo.setFasting(fasting);
	 * obrInfo.setPriority(priority); obrInfo.setAssessment(assessment);
	 * 
	 * return obrInfo;
	 * 
	 * }
	 */

	private String getObrDiagnosticName(OBR patient_obr) {
		String diagnosticName = "N/A";
		try {
			String[] obr04 = patient_obr.getObr4_UniversalServiceID().encode().split("\\^");
			if (obr04.length > 1 && obr04[1] != null && !obr04[1].equals(""))
				diagnosticName = obr04[1];
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			log.error("Exception occured : ", e);
		}
		return diagnosticName;
	}

	private String getObrCode(OBR patient_obr) {
		String code = "N/A";
		try {
			String[] obr04 = patient_obr.getObr4_UniversalServiceID().encode().split("\\^");
			if (obr04[0] != null && !obr04[0].equals(""))
				code = obr04[0];
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			log.error("Exception occured : ", e);
		}
		return code;
	}

	private String getObrFasting(OBR patient_obr) {
		String fasting = "N/A";
		try {
			if (patient_obr.getObr19_PlacerField2().encode() != null
					&& !patient_obr.getObr19_PlacerField2().encode().equals("")) {
				if (patient_obr.getObr19_PlacerField2().encode().equals("1"))
					fasting = "Fasting";
				else
					fasting = "Non-fasting";
			} else
				fasting = "Non-fasting";
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return fasting;
	}

	private String getObrPriority(OBR patient_obr) {
		String priority = "N/A";
		try {
			if (patient_obr.getObr5_PriorityNotused().encode() != null
					&& !patient_obr.getObr5_PriorityNotused().encode().equals("")) {
				if (patient_obr.getPriorityNotused().encode().equals("1"))
					priority = "stat";
				else
					priority = "Regular";
			} else
				priority = "Regular";
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return priority;
	}

	/*
	 * private String getObrAssessment(ORM_O01 ormMessage) {
	 * 
	 * String assessment = "N/A";
	 * 
	 * try { Structure[] patient_assessments =
	 * ormMessage.getORDER().getORDER_DETAIL().getAll("DG1"); for (Structure
	 * patient_assessment : patient_assessments) { if (((DG1)
	 * patient_assessment).getDiagnosisDescription().encode() != null && !((DG1)
	 * patient_assessment).getDiagnosisDescription().encode().equals("")) {
	 * 
	 * if (assessment.equals("N/A")) assessment = "- " + ((DG1)
	 * patient_assessment).getDiagnosisDescription().encode(); else assessment +=
	 * " \n- " + ((DG1) patient_assessment).getDiagnosisDescription().encode();
	 * 
	 * } } } catch (HL7Exception e) { log.error("Exception occured : ", e); } return
	 * assessment; }
	 */

	private String getObrAssessment(ORM_O01 ormMessage, String name) {

		String assessment = "N/A";

		try {
			Structure[] patient_assessments = ormMessage.getORDER().getORDER_DETAIL().getAll(name);
			for (Structure patient_assessment : patient_assessments) {
				if (((DG1) patient_assessment).getDiagnosisDescription().encode() != null
						&& !((DG1) patient_assessment).getDiagnosisDescription().encode().equals("")) {

					if (assessment.equals("N/A"))
						assessment = "- " + ((DG1) patient_assessment).getDiagnosisDescription().encode();
					else
						assessment += " \n- " + ((DG1) patient_assessment).getDiagnosisDescription().encode();

				}
			}
		} catch (HL7Exception e) {
			log.error("Exception occured : ", e);
		}
		return assessment;
	}

}

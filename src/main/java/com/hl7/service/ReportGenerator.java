package com.hl7.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportGenerator {

	// FileName : name of pdf file
	// params : contains patient related info
	// orderRequest: contains OBR info
	private static Logger log = LoggerFactory.getLogger(ReportGenerator.class);

	public void generateAndSavePdf(String fileName, Map<String, Object> parameters) {

		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport("src/main/resources/RequestReport.jasper", parameters,
					new JREmptyDataSource());

			OutputStream out = new FileOutputStream(new File(fileName));

			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			log.debug("PDF generated and saved as {}", fileName);

		} catch (JRException e) {
			log.error("Exception occured : ", e);
		} catch (FileNotFoundException e) {
			log.error("Exception occured : ", e);
		}

	}
}

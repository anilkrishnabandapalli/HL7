package com.hl7.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

	@Value("${application.savepath}")
	private String path;

	@Value("classpath:RequestReport.jasper")
	Resource pdfTemplate;

	public void generateAndSavePdf(String fileName, Map<String, Object> parameters) {

		JasperPrint jasperPrint = null;
		LocalDate date = LocalDate.now();
		String homeDir = System.getProperty("user.home");
		try(OutputStream out = new FileOutputStream(new File(homeDir + "/" + path + "/" + date.toString() + "/" + fileName))) {
			
			jasperPrint = JasperFillManager.fillReport(pdfTemplate.getInputStream(), parameters,
					new JREmptyDataSource());

			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			log.info("PDF generated and saved as {}", fileName);
			
		} catch (JRException e) {
			log.error("Unable to generate PDF");
			log.error("Exception occured : ", e);
		} catch (FileNotFoundException e) {
			log.error("Template not found for report");
			log.error("Exception occured : ", e);
		} catch (IOException e) {
			log.error("Exception occured : ", e);
		}
	}
}

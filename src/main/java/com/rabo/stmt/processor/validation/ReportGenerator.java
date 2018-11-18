package com.rabo.stmt.processor.validation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.ValidationReportDTO;
import com.rabo.stmt.processor.util.ProcessorConstants;

/*
 * Generate XML file for the consolidated report
 * 
 *  @author kalpperu
 *  
 */
@Component
public class ReportGenerator {

	@Value("${report.name}")
	private String reportName;

	private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

	public void generateXmlReport(ValidationReportDTO report, String outputDirectory) throws ProcessorException {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String fileName = outputDirectory + reportName + ProcessorConstants.UNDERSCORE + dateFormat.format(date)
				+ ProcessorConstants.XML_EXTENSION;
		logger.info("report name : " + fileName);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ValidationReportDTO.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			jaxbMarshaller.marshal(report, new File(fileName));
			logger.info("Report has been generated");
		} catch (JAXBException e) {
			throw new ProcessorException(e);
		}

	}

}

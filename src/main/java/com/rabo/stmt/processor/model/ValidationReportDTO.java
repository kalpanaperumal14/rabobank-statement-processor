package com.rabo.stmt.processor.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Entity for final report of all validated statements
 * 
 * @author kalpperu
 * 
 */
@XmlRootElement(name = "validationReport")
public class ValidationReportDTO {
	private List<ValidationRecordDTO> validationRecords = new ArrayList<ValidationRecordDTO>();

	@XmlElement(name = "report")
	public List<ValidationRecordDTO> getValidationRecords() {
		return validationRecords;
	}

	public void addRecord(ValidationRecordDTO record) {
		validationRecords.add(record);
	}
}
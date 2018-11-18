package com.rabo.stmt.processor.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/*
 * Entity for validation of every statement
 * 
 * @author kalpperu
 * 
 */
public class ValidationRecordDTO {

	private String fileName;

	private List<ValidationResultDTO> validationResult = new ArrayList<ValidationResultDTO>();

	public ValidationRecordDTO(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@XmlElementWrapper(name = "validationResult")
	@XmlElement(name = "result")
	public List<ValidationResultDTO> getValidationResult() {
		return validationResult;
	}

	public void addResults(List<ValidationResultDTO> results) {
		validationResult.addAll(results);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidationRecord {");
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", validationResult=");
		builder.append(validationResult);
		builder.append("}");
		return builder.toString();
	}

}
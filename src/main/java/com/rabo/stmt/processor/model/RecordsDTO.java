package com.rabo.stmt.processor.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Entity for root element of statement record
 * 
 * @author kalpperu
 * 
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.NONE)
public class RecordsDTO {

	@XmlElement(name = "record")
	private List<StatementDTO> statementDTO;

	public List<StatementDTO> getStatementDTO() {
		return statementDTO;
	}

	public void setStatementDTO(List<StatementDTO> statementDTO) {
		this.statementDTO = statementDTO;
	}

}
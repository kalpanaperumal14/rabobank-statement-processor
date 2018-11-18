package com.rabo.stmt.processor.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.opencsv.bean.CsvBindByPosition;

/*
 * Entity for statement record
 * 
 * @author kalpperu
 * 
 */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.NONE)
public class StatementDTO {

	@XmlAttribute(name = "reference")
	@CsvBindByPosition(position = 0)
	private Long reference;

	@XmlElement(name = "accountNumber")
	@CsvBindByPosition(position = 1)
	private String accountNumber;

	@XmlElement(name = "description")
	@CsvBindByPosition(position = 2)
	private String description;

	@XmlElement(name = "startBalance")
	@CsvBindByPosition(position = 3)
	private BigDecimal startBalance;

	@XmlElement(name = "mutation")
	@CsvBindByPosition(position = 4)
	private BigDecimal mutation;

	@XmlElement(name = "endBalance")
	@CsvBindByPosition(position = 5)
	private BigDecimal endBalance;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatementDTO {");
		builder.append("reference=");
		builder.append(reference);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", startBalance=");
		builder.append(startBalance);
		builder.append(", mutation=");
		builder.append(mutation);
		builder.append(", endBalance=");
		builder.append(endBalance);
		builder.append("}");
		return builder.toString();
	}
}

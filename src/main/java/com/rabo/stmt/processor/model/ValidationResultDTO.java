package com.rabo.stmt.processor.model;

/*
 * Entity for validation result of every record
 * 
 * @author kalpperu
 * 
 */
public class ValidationResultDTO {

	private Long reference;

	private String description;

	public ValidationResultDTO(Long reference, String description) {
		this.reference = reference;
		this.description = description;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Validation Result {reference=");
		builder.append(reference);
		builder.append(", description=");
		builder.append(description);
		builder.append("}");
		return builder.toString();
	}

}

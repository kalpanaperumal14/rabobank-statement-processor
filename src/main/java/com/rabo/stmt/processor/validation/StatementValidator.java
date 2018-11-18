package com.rabo.stmt.processor.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.stmt.processor.exception.ErrorCodes;
import com.rabo.stmt.processor.exception.ErrorMessages;
import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.StatementDTO;
import com.rabo.stmt.processor.model.ValidationResultDTO;

/*
 * Validate every record of statement
 * Add errors in ValidationResultDTO to generate consolidated report
 * 
 * @author kalpperu
 * 
 */
@Component
public class StatementValidator {

	@Autowired
	ErrorMessages errorMessages;

	private List<ValidationResultDTO> validationErrors = new ArrayList<ValidationResultDTO>();;

	private List<Long> validatedRecords = new ArrayList<Long>();

	Logger logger = LoggerFactory.getLogger(StatementValidator.class);

	public List<ValidationResultDTO> getValidationErrors() {
		return validationErrors;
	}

	// validate each record of statement
	public void validateRecord(StatementDTO record) throws ProcessorException {
		String error_description;
		if (record != null) {
			Long reference = record.getReference();
			if (reference != null) {
				if (validatedRecords.contains(reference)) {
					error_description = errorMessages.getErrorMessage(ErrorCodes.DUP_TRANS_REFERENCE, reference,
							record.getAccountNumber());
					ValidationResultDTO error = new ValidationResultDTO(reference, error_description);
					validationErrors.add(error);
				} else {
					validatedRecords.add(reference);
					BigDecimal result = record.getStartBalance().add(record.getMutation());
					if (result.compareTo(record.getEndBalance()) != 0) {
						error_description = errorMessages.getErrorMessage(ErrorCodes.WRONG_END_BALANCE, result,
								record.getEndBalance());
						ValidationResultDTO error = new ValidationResultDTO(reference, error_description);
						validationErrors.add(error);
					}
				}
			}
		}
	}

	public void reset() {
		validatedRecords.clear();
		validationErrors.clear();
	}
}

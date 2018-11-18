package com.rabo.stmt.processor.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.rabo.stmt.processor.beans.LocaleResolver;

/*
 * Retrieve locale specific error message for error code
 * 
 * @author kalpperu
 * 
 */
@Component
public class ErrorMessages {

	@Autowired
	@Qualifier("message")
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	public String getErrorMessage(String errorCode) throws ProcessorException {
		if (errorCode == null) {
			String errMsg = messageSource.getMessage(ErrorCodes.INPUT_FILE_NULL, null, localeResolver.getLocale());
			throw new ProcessorException(errMsg);
		}
		return messageSource.getMessage(errorCode, null, localeResolver.getLocale());
	}

	// To throw validation message
	public String getErrorMessage(String errorCode, Object... args) throws ProcessorException {
		if (errorCode == null) {
			String errMsg = messageSource.getMessage(ErrorCodes.INPUT_FILE_NULL, null, localeResolver.getLocale());
			throw new ProcessorException(errMsg);
		}
		return messageSource.getMessage(errorCode, args, localeResolver.getLocale());
	}

}

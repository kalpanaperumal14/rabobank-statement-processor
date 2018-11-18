package com.rabo.stmt.processor.exception;

/*
 * Use to throw application specific exception
 * 
 * @author kalpperu
 * 
 */
public class ProcessorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProcessorException(String message) {
		super(message);
	}

	public ProcessorException(Exception exception) {
		super(exception);
	}
}

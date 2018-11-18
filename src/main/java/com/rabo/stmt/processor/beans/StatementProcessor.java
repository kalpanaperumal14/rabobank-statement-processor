package com.rabo.stmt.processor.beans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.stmt.processor.exception.ErrorCodes;
import com.rabo.stmt.processor.exception.ErrorMessages;
import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.StatementDTO;
import com.rabo.stmt.processor.model.ValidationRecordDTO;
import com.rabo.stmt.processor.model.ValidationReportDTO;
import com.rabo.stmt.processor.reader.CsvStatementReader;
import com.rabo.stmt.processor.reader.XmlStatementReader;
import com.rabo.stmt.processor.util.ProcessorConstants;
import com.rabo.stmt.processor.validation.ReportGenerator;
import com.rabo.stmt.processor.validation.StatementValidator;

/*
 * Unmarshal csv or xml files available in input directory 
 * passed as command line arguments
 *
 * Validate duplicate reference and wrong end balance
 * 
 * Generate validation report in the output directory
 * passed as command line arguments
 * 
 * @author kalpperu
 * 
 */
@Component
public class StatementProcessor implements ErrorCodes {
	@Autowired
	private ErrorMessages errorMessages;

	@Autowired
	private CsvStatementReader csvReader;

	@Autowired
	private XmlStatementReader xmlReader;

	@Autowired
	private StatementValidator validator;

	@Autowired
	private ReportGenerator reportGenerator;

	Logger logger = LoggerFactory.getLogger(StatementProcessor.class);

	public void process(String... args) throws ProcessorException {

		ValidationReportDTO report = new ValidationReportDTO();

		List<StatementDTO> statements = null;

		// Check input directory is available
		if (args == null || args.length == 0) {
			throw new ProcessorException(errorMessages.getErrorMessage(ErrorCodes.INPUT_DIRECTORY_MISSING));
		} else {
			try {
				// Process all the csv and xml files in the input directory
				Iterator<Path> iterator = Files.newDirectoryStream(Paths.get(args[0]),
						path -> path.toString().endsWith(ProcessorConstants.CSV_EXTENSION)
								|| path.toString().endsWith(ProcessorConstants.XML_EXTENSION))
						.iterator();
				String outputDirectory = args.length > 1 ? args[1] : args[0];

				while (iterator.hasNext()) {

					Path path = iterator.next();
					logger.info("file : " + path);

					// Unmarshalling CSV or XML file
					if (path.toString().endsWith(ProcessorConstants.CSV_EXTENSION)) {
						statements = csvReader.readCSVFile(path);
					} else if (path.toString().endsWith(ProcessorConstants.XML_EXTENSION)) {
						statements = xmlReader.readXmlFile(path);
					}

					// Validate duplicate reference and wrong end balance
					for (StatementDTO statement : statements) {
						validator.validateRecord(statement);
					}

					ValidationRecordDTO record = new ValidationRecordDTO(path.toString());
					record.addResults(validator.getValidationErrors());

					// Add validation results
					report.addRecord(record);

					// Reset to validate next statement
					validator.reset();
				}
				// Generate output report
				outputDirectory += ProcessorConstants.FORWARD_SLASH;
				reportGenerator.generateXmlReport(report, outputDirectory);
			} catch (IOException e) {
				throw new ProcessorException(errorMessages.getErrorMessage(ErrorCodes.STATEMENT_READ_FAILED));
			}
		}

	}
}

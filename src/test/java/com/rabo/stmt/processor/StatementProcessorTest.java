package com.rabo.stmt.processor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.stmt.processor.beans.StatementProcessor;
import com.rabo.stmt.processor.exception.ErrorCodes;
import com.rabo.stmt.processor.exception.ErrorMessages;
import com.rabo.stmt.processor.exception.ProcessorException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProcessorApplication.class)
@TestPropertySource("classpath:application.properties")
public class StatementProcessorTest {

	private static final String INPUT_DIRECTORY = "E:\\statements";
	private static final String OUTPUT_DIRECTORY = "E:\\statements\\reports";
	@Value("${report.name}")
	private String reportName;

	@Autowired
	ErrorMessages errorMessages;

	@Autowired
	StatementProcessor statementProcessor;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testInputDirMissing() throws ProcessorException {
		thrown.expect(ProcessorException.class);
		thrown.expectMessage(errorMessages.getErrorMessage(ErrorCodes.INPUT_DIRECTORY_MISSING));
		statementProcessor.process((String[]) null);
	}

	@Test
	public void testReportGenerated() throws ProcessorException {

		try {
			statementProcessor.process(INPUT_DIRECTORY, OUTPUT_DIRECTORY);

			// Verify whether the reports are generated.
			Iterator<Path> iterator = Files
					.newDirectoryStream(Paths.get(OUTPUT_DIRECTORY), path -> path.toString().endsWith(".xml"))
					.iterator();
			boolean foundReport = false;
			while (iterator.hasNext()) {
				if (iterator.next().getFileName().toString().startsWith(reportName)) {
					foundReport = true;
					break;
				}
			}
			assertTrue(foundReport);
		} catch (IOException e) {
			fail("Failed reading statements");

		}

	}
}

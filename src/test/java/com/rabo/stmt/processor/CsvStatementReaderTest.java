package com.rabo.stmt.processor;

import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.stmt.processor.exception.ErrorMessages;
import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.StatementDTO;
import com.rabo.stmt.processor.reader.CsvStatementReader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProcessorApplication.class)
@TestPropertySource("classpath:application.properties")

public class CsvStatementReaderTest {
	@Autowired
	ErrorMessages errorMessages;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private String file = null;

	@Before
	public void setup() {
		file = "files/records.csv";
		/*
		 * statementDTO = new StatementDTO();
		 * statementDTO.setReference(194261L);
		 * statementDTO.setAccountNumber("NL91RABO0315273637");
		 * statementDTO.setDescription("Clothes from Jan Bakker");
		 * statementDTO.setStartBalance(new BigDecimal("21.6"));
		 * statementDTO.setMutation(new BigDecimal("-41.83"));
		 * statementDTO.setEndBalance(new BigDecimal("-20.23"));
		 */}

	@Test(expected = NullPointerException.class)
	public void testInputIsNull() throws ProcessorException {
		CsvStatementReader csvStatementReader = new CsvStatementReader();
		assertNotNull(csvStatementReader);
		csvStatementReader.readCSVFile(null);
	}

	@Test
	public void testRecordsAvailable() throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource(file).toURI());
		List<StatementDTO> statements = new CsvStatementReader().readCSVFile(path);
		assertNotNull(statements);
	}

}

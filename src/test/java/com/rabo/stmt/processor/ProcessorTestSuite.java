package com.rabo.stmt.processor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ XmlStatementReaderTest.class, CsvStatementReaderTest.class, StatementValidatorTest.class,
		StatementProcessorTest.class })
public class ProcessorTestSuite {

}

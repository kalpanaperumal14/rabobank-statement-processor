package com.rabo.stmt.processor;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.stmt.processor.exception.ErrorMessages;
import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.StatementDTO;
import com.rabo.stmt.processor.model.ValidationResultDTO;
import com.rabo.stmt.processor.validation.StatementValidator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProcessorApplication.class)
@TestPropertySource("classpath:application.properties")
public class StatementValidatorTest {

	@Autowired
	ErrorMessages errorMessages;

	@Autowired
	StatementValidator validator;

	private StatementDTO stmt;
	private StatementDTO stmtRefNo;

	private StatementDTO endBal;

	private StatementDTO stmtCorrectEndBal;

	@Before
	public void setup() throws ProcessorException {
		validator.reset();

		stmt = new StatementDTO();
		stmt.setReference(112806L);
		stmt.setAccountNumber("NL27SNSB0917829871");
		stmt.setDescription("Clothes for Willem Dekker");
		stmt.setStartBalance(new BigDecimal("91.23"));
		stmt.setMutation(new BigDecimal("+15.57"));
		stmt.setEndBalance(new BigDecimal("106.8"));

		stmtRefNo = new StatementDTO();
		stmtRefNo.setReference(112806L);
		stmtRefNo.setAccountNumber("NL69ABNA0433647324");
		stmtRefNo.setDescription("Clothes for Richard de Vries");
		stmtRefNo.setStartBalance(new BigDecimal("90.83"));
		stmtRefNo.setMutation(new BigDecimal("-10.91"));
		stmtRefNo.setEndBalance(new BigDecimal("79.92"));

		endBal = new StatementDTO();
		endBal.setReference(167875L);
		endBal.setAccountNumber("NL93ABNA0585619023");
		endBal.setDescription("Tickets from Erik de Vries");
		endBal.setStartBalance(new BigDecimal("5429"));
		endBal.setMutation(new BigDecimal("-939"));
		endBal.setEndBalance(new BigDecimal("6368"));

		stmtCorrectEndBal = new StatementDTO();
		stmtCorrectEndBal.setReference(135607L);
		stmtCorrectEndBal.setAccountNumber("NL27SNSB0917829871");
		stmtCorrectEndBal.setDescription("Subscription from Peter Theuß");
		stmtCorrectEndBal.setStartBalance(new BigDecimal("70.42"));
		stmtCorrectEndBal.setMutation(new BigDecimal("+34.42"));
		stmtCorrectEndBal.setEndBalance(new BigDecimal("104.84"));
	}

	@Test
	public void testDuplicates() throws ProcessorException {
		List<ValidationResultDTO> validationErrors = validator.getValidationErrors();
		validator.validateRecord(stmt);
		assertThat(validationErrors, hasSize(0));

		validator.validateRecord(stmtRefNo);
		assertThat(validationErrors, hasSize(1));
	}

	@Test
	public void testWrongEndBalance() throws ProcessorException {
		List<ValidationResultDTO> validationErrors = validator.getValidationErrors();
		validator.validateRecord(endBal);
		assertThat(validationErrors, hasSize(1));
	}

	@Test
	public void testCorrectEndBalance() throws ProcessorException {
		List<ValidationResultDTO> validationErrors = validator.getValidationErrors();
		validator.validateRecord(stmtCorrectEndBal);
		assertThat(validationErrors, hasSize(0));
	}
}

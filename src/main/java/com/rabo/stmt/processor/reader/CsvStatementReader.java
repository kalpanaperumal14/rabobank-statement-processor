package com.rabo.stmt.processor.reader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.StatementDTO;

/*
 * Reader for CSV files
 * 
 * @author kalpperu
 * 
 */
@Component
public class CsvStatementReader {

	public List<StatementDTO> readCSVFile(Path path) throws ProcessorException {
		List<StatementDTO> statements = null;
		try (Reader reader = Files.newBufferedReader(path);) {
			ColumnPositionMappingStrategy<StatementDTO> strategy = new ColumnPositionMappingStrategy<StatementDTO>();
			strategy.setType(StatementDTO.class);
			CsvToBean<StatementDTO> bean = new CsvToBeanBuilder<StatementDTO>(reader).withType(StatementDTO.class)
					.withSkipLines(1).withMappingStrategy(strategy).build();
			statements = bean.parse();
		} catch (IOException exception) {
			throw new ProcessorException(exception);
		}

		return statements;
	}

	public static void main(String[] args) throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource("files/records.csv").toURI());
		new CsvStatementReader().readCSVFile(path).stream().forEach(System.out::println);
	}
}

package com.rabo.stmt.processor.reader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.rabo.stmt.processor.exception.ProcessorException;
import com.rabo.stmt.processor.model.RecordsDTO;
import com.rabo.stmt.processor.model.StatementDTO;

/*
 * Reader for XML files
 * 
 * @author kalpperu
 * 
 */

@Component
public class XmlStatementReader {

	public List<StatementDTO> readXmlFile(Path path) throws ProcessorException {
		List<StatementDTO> statements = null;
		try (Reader reader = Files.newBufferedReader(path);) {
			JAXBContext jaxbContext = JAXBContext.newInstance(RecordsDTO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			RecordsDTO records = (RecordsDTO) unmarshaller.unmarshal(reader);
			statements = records.getStatementDTO();
		} catch (JAXBException e) {
			throw new ProcessorException("JAXBException");
		} catch (IOException e) {
			throw new ProcessorException("Input file is null");
		}
		return statements;
	}

	public static void main(String[] args) throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource("files/records.xml").toURI());
		new XmlStatementReader().readXmlFile(path).stream().forEach(System.out::println);
	}
}

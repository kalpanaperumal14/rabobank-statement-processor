package com.rabo.stmt.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.rabo.stmt.processor.beans.StatementProcessor;

/*
 * Run application by passing command line arguments
 * 
 * @author kalpperu
 * 
 */
@SpringBootApplication
public class ProcessorApplication implements CommandLineRunner {

	@Autowired
	private StatementProcessor statementProcessor;

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		statementProcessor.process(args);
	}

	@Bean(name = "message")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/messages");
		return messageSource;
	}

}

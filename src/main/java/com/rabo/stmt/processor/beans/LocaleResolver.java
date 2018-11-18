package com.rabo.stmt.processor.beans;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 * Locale details for message resources and application specific messages
 * 
 * @author kalpperu
 * 
 */
@Component
public class LocaleResolver {
	@Value("${application.locale.language}")
	private String language;

	@Value("${application.locale.country}")
	private String country;

	private Locale locale;

	@PostConstruct
	public void init() {
		this.locale = new Locale(language, country);
	}

	public Locale getLocale() {
		return this.locale;
	}

}

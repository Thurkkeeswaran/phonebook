package com.cg.phonebook;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// Replace YOUR_APP with name of class containing 'public static void main'
		return application.sources(PhoneBookApplication.class);
	}
}

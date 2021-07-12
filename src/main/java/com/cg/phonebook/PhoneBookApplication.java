package com.cg.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PhoneBookApplication {

	private static final Logger logger = LoggerFactory.getLogger(PhoneBookApplication.class);

	public static void main(String[] args) {
		logger.info("OPEN");
		SpringApplication.run(PhoneBookApplication.class, args);
	}

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(metadata()).select().paths(regex("/api/phonebook.*"))
				.build();
	}

	@SuppressWarnings("deprecation")
	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("Phonebook Application").description("API to add contacts")
				.termsOfServiceUrl("https://www.javaPhonebook.com/").contact("Phonebook team").version("2.0").build();
	}

}

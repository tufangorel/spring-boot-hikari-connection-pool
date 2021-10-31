package com.company.customerinfo;

import com.company.customerinfo.config.listener.ApplicationFailedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication( exclude = {DataSourceAutoConfiguration.class} )
public class CustomerInfoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInfoApplication.class.getName());

	public static void main(String[] args) {

		new SpringApplicationBuilder(CustomerInfoApplication.class)
				.listeners(new ApplicationFailedEventListener())
				.run(args);

		LOGGER.info("Spring Boot application started!");
	}

}
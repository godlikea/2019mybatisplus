package com.kai.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
	
	@Bean
	ServletRegistrationBean h2SevletRegistrationBean() {
		ServletRegistrationBean registration=new ServletRegistrationBean<>(new WebServlet());
		registration.addUrlMappings("/h2-console/*");
		return registration;
	}

}

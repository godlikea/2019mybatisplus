package com.kai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger config
 * 接口地址 http://localhost:8010/swagger-ui.html
 * @author ggk
 * @data 2019年3月23日上午11:18:44
 */
@Configuration
public class Swagger2 {

	@Bean
	public Docket  createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.kai.controller"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("接口文档")
				.description("github地址").termsOfServiceUrl("http://localhost:8010/swagger")
				.version("1.0").build();
	}
}

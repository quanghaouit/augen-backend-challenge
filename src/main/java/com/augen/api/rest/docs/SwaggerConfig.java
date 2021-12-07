package com.augen.api.rest.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(new ApiInfoBuilder().title("IOT")
						.description("Description request and response of API as sample").version("1.0").license("")
						.licenseUrl("").build())
				.select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).build();
	}

}

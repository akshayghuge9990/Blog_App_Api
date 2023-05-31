package com.Akshay.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	// swagger Url= http://localhost:8888/swagger-ui/index.html
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	

	private ApiKey apiKeys() {
		
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
		
	}
	
	private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts() {
		
		return Arrays.asList(springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(sf()).build());
	}
	
	private List<SecurityReference> sf() {
		
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
		
	}
	
	
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
				
		
		
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("Blogging Applilication : Backend Cources", "This project is Developed by Akshay", "1.0", "Terms of Service", new Contact("Akshay","https://ghugeakshay.com","ghugeakshay9990@gmail.com") , "License of APIS", "API license URL",Collections.emptyList());
	}

}

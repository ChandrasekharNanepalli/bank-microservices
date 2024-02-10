package com.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import com.loan.dto.LoansContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value= {LoansContactInfoDto.class})
@OpenAPIDefinition(info =
						@Info(title = "Loan microservice REST API Documentation", 
						  	description = "Bank Cards microservice REST API Documentation", 
						  	version = "v1", contact = @Contact(name = "Chandrasekhar", 
						  	email = "nanepallichandu711@gmail.com")), 
					externalDocs = @ExternalDocumentation(description = "Bank Cards MicroService RestApi", 
														  url = "https://www.chandu.com/swagger-ui.html"))
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}

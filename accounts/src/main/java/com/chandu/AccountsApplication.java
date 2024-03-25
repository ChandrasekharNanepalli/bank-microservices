package com.chandu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.chandu.dto.AccountsContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication    
@EnableConfigurationProperties(value= {AccountsContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef  = "aduit")
@EnableFeignClients
@OpenAPIDefinition(
		info=@Info(
				title = "Accounts microservices REST API Documentation",
				description = "Bank Accounts Microservice Rest Api",
				version = "v1",
				contact = @Contact(name="Chandrasekhar",
									email = "nanepallichandu711@gmail.com"
						)
				),
		
		externalDocs = @ExternalDocumentation(
				description = "Bank Accounts MicroService RestApi",
				url="https://www.chandu.com/swagger-ui.html"
				)
		
		)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}
	
	
}

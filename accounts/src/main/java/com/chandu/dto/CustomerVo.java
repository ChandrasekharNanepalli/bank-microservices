package com.chandu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(name="Customer",
		description = "schema to hold Customer And Account Information"
		)
public class CustomerVo {
	
	@NotEmpty(message = "name cannot be empty")
	@Size(min=5,max=30,message="the length of customer name between 5 & 30")
	@Schema(description ="Name of the customer",example = "chandu"
	)
	private String name;
	@NotEmpty(message = "email cannot be empty")
	@Email(message="Email address should be a valid value")
	@Schema(description ="email of the customer",example = "abc@gmail.com"
			)
	private String email;
	@Pattern(regexp = "(^[0-9]{10})",message = "mobile number must be 10 Digit")
	@Schema(description ="MobileNummber of the customer",example = "56897841230"
			)
	private String mobileNumber;
	@Schema(description ="Account Details of the customer")
	private AccountVo accountVo;

}

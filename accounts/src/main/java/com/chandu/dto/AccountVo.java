package com.chandu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(name="Accounts",
description = "schema to hold  Account Information"
)
public class AccountVo {
	
	@NotEmpty(message="AccountNumber can not be null or empty")
	@Pattern(regexp = "(^$[0-9]{10})",message = "Account number must not be null")
	@Schema(description = "Account Number of the Customer"
	)
	private Long accountsNumber;
	@NotEmpty(message = "Account Type can not be a null or empty")
	@Schema(description = "Account Type of the Customer"
			)
	private String accountType;
	@NotEmpty(message="BranchAddress can not be a null or empty")
	@Schema(description = "Branch Address of the Customer"
			)
	private String branchAddress;

}

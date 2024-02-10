package com.chandu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chandu.constants.AccountsConstants;
import com.chandu.dto.AccountsContactInfoDto;
import com.chandu.dto.CustomerVo;
import com.chandu.dto.ErrorResponseVo;
import com.chandu.dto.ResponseVo;
import com.chandu.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})

@Validated
@Tag(name="CRUD REST Apis for Account in Bank",
	description = "CRUD REST Apis in Bank to Create,Update,Fetch and Delete Account Details "	
		)
public class AccountsController {
	
	@Autowired
	private IAccountsService accountsService;
	
	@Autowired
	private AccountsContactInfoDto accountsContactInfoDto;
	
	@Autowired
	private Environment environment;
	
	@Value("${build.version}")
	private String buildVersion;
	
	
	@Operation(
			summary = "Create Account Details Rest Api",
			description = "Rest Api to create customer & Account inside Bank"
			)
	@ApiResponses({
	@ApiResponse(
			responseCode ="200",
			description = "HTTP Status Created"
			),
	@ApiResponse(
			responseCode ="500",
			description = "HTTP Status Internal Server Error",
			content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
			)
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseVo> createAccount(@Valid @RequestBody CustomerVo customerVo){
		accountsService.createAccount(customerVo);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseVo(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
	}
	
	@Operation(
			summary = "Fetch Account Details Rest Api",
			description = "Rest Api to fetch customer & Account inside Bank"
			)
	@ApiResponses({
	@ApiResponse(
			responseCode ="200",
			description = "HTTP Status OK"
			),
	@ApiResponse(
					responseCode ="500",
					description = "HTTP Status Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
					)})
	@GetMapping("/fetch")
	@ResponseBody
	public ResponseEntity<CustomerVo> fetchDetails(@RequestParam(value = "mobile")
	@Pattern(regexp = "(^[0-9]{10})",message = "mobile number must be 10 Digit") String mobile ){
		System.out.println("function");
		CustomerVo customerVo=accountsService.fetchAccount(mobile);
		System.out.println("customervo, "+customerVo);
		return ResponseEntity.status(HttpStatus.OK)
							.body(customerVo);
		
	}

	@Operation(
			summary = "Update Account Details Rest Api",
			description = "Rest Api to update customer & Account details based on Account Number"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode ="200",
				description = "HTTP Status OK"
				),
		@ApiResponse(
				responseCode ="417",
				description = "Exception Failed"
				
				),
		@ApiResponse(
				responseCode ="500",
				description = "HTTP Status Internal Server Error",
				content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
				)
		
	})
	@PutMapping("/update")
	public ResponseEntity<ResponseVo> updateDetails(@Valid @RequestBody CustomerVo customervo ){
		
		boolean isUpdated=accountsService.updateAccount(customervo);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseVo(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseVo(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
		
		
	}
	
	@Operation(
			summary = "Delete Account & Customer Details Rest Api",
			description = "Rest Api to delete customer & Account details based on MobileNumber"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode ="200",
				description = "HTTP Status OK"
				),
		@ApiResponse(
				responseCode ="417",
				description = "Exception Failed"
				
				),
		@ApiResponse(
				responseCode ="500",
				description = "HTTP Status Internal Server Error",
				content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
				)
		
	})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseVo> deleteAccountDetails(@RequestParam(value = "mobile") 
	@Pattern(regexp = "(^[0-9]{10})",message = "mobile number must be 10 Digit") String mobile){
		
		boolean isDeleted=accountsService.deleteAccount(mobile);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseVo(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseVo(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELTE));
		
		
	}
	
	
	@Operation(
			summary = "Get Build Information",
			description = "Get Build Information that is deployed into accounts microservices"
			)
	@ApiResponses({
	@ApiResponse(
			responseCode ="200",
			description = "HTTP Status OK"
			),
	@ApiResponse(
					responseCode ="500",
					description = "HTTP Status Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
					)})
	@GetMapping("/build-info")
	public ResponseEntity<String> grtBuildInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}
	
	
	@Operation(
			summary = "Get java  Version",
			description = "Get Java Information that is deployed into accounts microservices"
			)
	@ApiResponses({
	@ApiResponse(
			responseCode ="200",
			description = "HTTP Status OK"
			),
	@ApiResponse(
					responseCode ="500",
					description = "HTTP Status Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
					)})
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion(){
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("Java_Home"));
	}
	
	@Operation(
			summary = "Get Contact Info",
			description = "Contact Info that can be reached out in case of any issue"
			)
	@ApiResponses({
	@ApiResponse(
			responseCode ="200",
			description = "HTTP Status OK"
			),
	@ApiResponse(
					responseCode ="500",
					description = "HTTP Status Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorResponseVo.class))
					)})
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
	}
	
}

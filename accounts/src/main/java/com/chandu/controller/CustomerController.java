package com.chandu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chandu.dto.CustomerDetailsVo;
import com.chandu.dto.ErrorResponseVo;
import com.chandu.service.ICustomerDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})

@Validated
@Tag(name="REST Apis for Chandu  Bank",
	description = "REST Apis for Chandu  Bank to Fetch Customer Details "	
		)
public class CustomerController {
	
	@Autowired
	private ICustomerDetails customerDetails;
	
	
	@Operation(
			summary = "Fetch Customer Details Rest Api",
			description = "Rest Api to fetch customer Details based on mobile number"
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
	@GetMapping("/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsVo> fetchCustomerDetails(@RequestParam("mobileNumber")
    														@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    														String mobileNumber){
		
		CustomerDetailsVo customerDetailsVo=customerDetails.fetchCustomerDetails(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsVo);
		
	}

}

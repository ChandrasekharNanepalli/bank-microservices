package com.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.loan.constants.LoansConstants;
import com.loan.dto.ErrorResponseVo;
import com.loan.dto.LoansContactInfoDto;
import com.loan.dto.LoansVo;
import com.loan.dto.ResponseVo;
import com.loan.service.ILoansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(
        name = "CRUD REST APIs for Loans in Bank",
        description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

	@Autowired
    private ILoansService iLoansService;
	
	@Autowired
	private LoansContactInfoDto loansContactInfoDto;
	
	@Autowired
	private Environment environment;
	
	@Value("${build.version}")
	private String buildVersion;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseVo.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseVo> createLoan(@RequestParam("mobileNumber")
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseVo(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseVo.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansVo> fetchLoanDetails(@RequestParam("mobileNumber")
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        LoansVo loansDto = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseVo.class)
                    )
            )
        }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseVo> updateLoanDetails(@Valid @RequestBody LoansVo loansDto) {
        boolean isUpdated = iLoansService.updateLoan(loansDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseVo(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseVo(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseVo.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseVo> deleteLoanDetails(@RequestParam("mobileNumber")
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseVo(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseVo(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
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
	public ResponseEntity<LoansContactInfoDto> getContactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
	}

}
package com.cards.controller;


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

import com.cards.constants.CardsConstants;
import com.cards.dto.CardsContactInfoDto;
import com.cards.dto.CardsVo;
import com.cards.dto.ErrorResponseVo;
import com.cards.dto.ResponseVo;
import com.cards.service.ICardsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;



@Tag(
        name = "CRUD REST APIs for Cards in Bank",
        description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})

@Validated
public class CardsController {
	
	@Autowired
    private ICardsService iCardsService;
	
	@Autowired
	private CardsContactInfoDto cardsContactInfoDto;
	
	@Autowired
	private Environment environment;
	
	@Value("${build.version}")
	private String buildVersion;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside Bank"
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
    public ResponseEntity<ResponseVo> createCard(@Valid @RequestParam("mobileNumber")
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        iCardsService.createCard(mobileNumber);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseVo(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
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
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsVo> fetchCardDetails(@RequestParam("mobileNumber")
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        CardsVo cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
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
        })
    @PutMapping("/update")
    public ResponseEntity<ResponseVo> updateCardDetails(@Valid @RequestBody CardsVo cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseVo(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseVo(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
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
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseVo> deleteCardDetails(@RequestParam("mobileNumber")
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseVo(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseVo(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
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
	public ResponseEntity<CardsContactInfoDto> getContactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
	}

}
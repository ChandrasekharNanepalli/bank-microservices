package com.chandu.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
@Schema(name="Error Response",
description = "Schema to hold error response information"
		)
public class ErrorResponseVo {
	
	@Schema(description = "Api Path Invoked By Client")
	private String apiPath;
	@Schema(description = "Error Code representing the error happend")
	private HttpStatus  httpStatus;
	@Schema(description = "Error message representing the error happend")
	private String errorMsg;
	@Schema(description = "Time representing when the error happend")
	private LocalDateTime errorTym;
}

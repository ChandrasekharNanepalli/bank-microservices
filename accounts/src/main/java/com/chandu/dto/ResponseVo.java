package com.chandu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(name=" Successfull Response",
description = "Schema to hold Successful response information"
		)
public class ResponseVo {
	
	@Schema(description = "Status code in the response"
			)
	private String statusCode;
	@Schema(description = "Status Message in the response"
			)
	private String responseCode;
	

}

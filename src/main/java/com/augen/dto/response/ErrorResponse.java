package com.augen.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Error response")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {

	@ApiModelProperty(value = "status", example = "false")
	private final Boolean status = false;

	@ApiModelProperty(value = "message", example = "error message")
	private String message;

	@ApiModelProperty(value = "code", example = "error code")
	@JsonInclude(Include.NON_EMPTY)
	private String errorCode;

	@ApiModelProperty(value = "errors", example = "error details")
	@JsonInclude(Include.NON_EMPTY)
	private List<String> errors;

	public ErrorResponse(String message) {
		this.message = message;
	}
}

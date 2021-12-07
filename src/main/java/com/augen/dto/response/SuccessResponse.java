package com.augen.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Success Response")
@Data
public class SuccessResponse {

	@ApiModelProperty(value = "status", example = "true")
	private final Boolean status = true;

}

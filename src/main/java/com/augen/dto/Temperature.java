package com.augen.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Temperature {

	@ApiModelProperty(value = "unit", example = "C")
	private String unit;

	@ApiModelProperty(value = "value", example = "23.3")
	private String value;
}

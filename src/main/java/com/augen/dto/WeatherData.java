package com.augen.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherData {

	@ApiModelProperty(value = "humidity", example = "100")
	private Integer humidity;

	@ApiModelProperty(value = "dtemperature", example = "{}")
	private Temperature temperature;

	@ApiModelProperty(value = "timestamp", example = "2021-12-05T21:09:23.829")
	private LocalDateTime timestamp;

}

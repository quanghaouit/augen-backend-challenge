package com.augen.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.augen.dto.WeatherData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDeviceDto {

	@ApiModelProperty(value = "deviceId", example = "xyz123")
	@NotBlank(message = "device id is required")
	private String deviceId;

	@ApiModelProperty(value = "latitude", example = "41.25")
	@NotNull(message = "latitude is required")
	private Double latitude;

	@ApiModelProperty(value = "longitude", example = "-23.3")
	@NotNull(message = "longitude is required")
	private Double longitude;

	@ApiModelProperty(value = "data", example = "{}")
	@NotNull(message = "data is required")
	private WeatherData data;
}

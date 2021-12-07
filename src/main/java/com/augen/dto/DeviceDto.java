package com.augen.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Device Info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDto {

	@ApiModelProperty(value = "device id", example = "xyz123")
	private String deviceId;

	@ApiModelProperty(value = "latitude", example = "41.25")
	private Double latitude;

	@ApiModelProperty(value = "longtitude", example = "-120.9762")
	private Double longitude;

	@ApiModelProperty(value = "data", example = "[]")
	private List<WeatherData> data;

}

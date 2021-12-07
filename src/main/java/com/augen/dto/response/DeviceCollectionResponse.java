package com.augen.dto.response;

import com.augen.dto.DeviceDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Device collection response")
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceCollectionResponse extends SuccessResponse {

	@ApiModelProperty(value = "device info", example = "true")
	private DeviceDto deviceInfo;

}

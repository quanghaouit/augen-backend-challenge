package com.augen.api.rest;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.augen.dto.DeviceDto;
import com.augen.dto.request.RegisterDeviceDto;
import com.augen.dto.response.DeviceCollectionResponse;
import com.augen.dto.response.ErrorResponse;
import com.augen.dto.response.SuccessResponse;
import com.augen.service.DeviceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@RestController
@RequestMapping(value = "/api/devices")
@Api(tags = { "Device Controller" })
public class DeviceController {
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private MessageSource messageSource;

	@ApiOperation(value = "Register weather data collect by device.", notes = "Register or add weather data if exist collection of device")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully", response = SuccessResponse.class),
			@ApiResponse(code = 401, message = "Parameter incorrect", response = ErrorResponse.class) })
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse register(@Valid @RequestBody RegisterDeviceDto registerDeviceDto) {
		deviceService.register(registerDeviceDto);
		return new SuccessResponse();
	}

	@ApiOperation(value = "Get device weather data collection", notes = "Get device weather data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully", response = DeviceCollectionResponse.class),
			@ApiResponse(code = 401, message = "Parameter incorrect", response = ErrorResponse.class) })
	@RequestMapping(value = "/{deviceId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getDeviceCollection(@NotBlank @PathVariable String deviceId,
			@RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
		DeviceDto deviceInfo = deviceService.getDeviceCollection(deviceId, startTime, endTime);

		if (ObjectUtils.isEmpty(deviceInfo)) {
			return new ResponseEntity<>(new ErrorResponse(messageSource.getMessage("device.not.found", null, null)),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(DeviceCollectionResponse.builder().deviceInfo(deviceInfo).build(), HttpStatus.OK);
	}

}

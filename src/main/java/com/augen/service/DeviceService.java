package com.augen.service;

import java.time.LocalDateTime;

import com.augen.dto.DeviceDto;
import com.augen.dto.request.RegisterDeviceDto;

public interface DeviceService {

	public void register(RegisterDeviceDto registerInfo);

	public DeviceDto getDeviceCollection(String deviceId, LocalDateTime startTime, LocalDateTime endTime);

}

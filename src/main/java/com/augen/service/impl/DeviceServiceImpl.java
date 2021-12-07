package com.augen.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augen.dao.entity.Device;
import com.augen.dao.repository.DeviceRepository;
import com.augen.dto.DeviceDto;
import com.augen.dto.WeatherData;
import com.augen.dto.request.RegisterDeviceDto;
import com.augen.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public void register(RegisterDeviceDto registerInfo) {
		Device entity = null;
		List<WeatherData> weatherData = new ArrayList<WeatherData>();

		Optional<Device> optionalEntity = deviceRepository.findById(registerInfo.getDeviceId());
		if (optionalEntity.isPresent()) {
			entity = optionalEntity.get();
			weatherData = entity.getData();
		} else {
			entity = Device.builder().deviceId(registerInfo.getDeviceId()).latitude(registerInfo.getLatitude())
					.longitude(registerInfo.getLongitude()).build();
		}

		WeatherData newData = registerInfo.getData();
		newData.setTimestamp(LocalDateTime.now());
		weatherData.add(newData);

		entity.setData(weatherData);

		deviceRepository.save(entity);
	}

	@Override
	public DeviceDto getDeviceCollection(String deviceId, LocalDateTime startTime, LocalDateTime endTime) {
		Optional<Device> optinalDevice = deviceRepository.findById(deviceId);
		if (optinalDevice.isPresent()) {

			Device device = optinalDevice.get();
			if (startTime != null && endTime != null) {
				device.setData(device.getData().stream()
						.filter(f -> (f.getTimestamp().equals(startTime) || f.getTimestamp().isAfter(startTime))
								&& (f.getTimestamp().equals(endTime) || f.getTimestamp().isBefore(endTime)))
						.collect(Collectors.toList()));
			} else if (startTime != null) {
				device.setData(device.getData().stream()
						.filter(f -> f.getTimestamp().equals(startTime) || f.getTimestamp().isAfter(startTime))
						.collect(Collectors.toList()));
			} else if (endTime != null) {
				device.setData(device.getData().stream()
						.filter(f -> f.getTimestamp().equals(endTime) || f.getTimestamp().isBefore(endTime))
						.collect(Collectors.toList()));
			}

			DeviceDto deviceDto = new DeviceDto();
			BeanUtils.copyProperties(device, deviceDto);

			return deviceDto;
		} else {
			return null;
		}

	}

}

package com.augen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.augen.dao.entity.Device;
import com.augen.dao.repository.DeviceRepository;
import com.augen.dto.DeviceDto;
import com.augen.dto.Temperature;
import com.augen.dto.WeatherData;
import com.augen.dto.request.RegisterDeviceDto;
import com.augen.service.impl.DeviceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

	@Mock
	private DeviceRepository deviceRepository;

	@InjectMocks
	private DeviceServiceImpl deviceService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void register_newDevice_thenRegisterNew() {
		RegisterDeviceDto device = createRegisterData();
		Mockito.when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.empty());

		deviceService.register(device);

		Device expectEntity = createDeviceEntity();
		verify(deviceRepository, times(1)).save(expectEntity);
	}

	@Test
	public void register_existDevice_thenAddWeatherDataToExistedList() {
		RegisterDeviceDto device = createRegisterData();
		Device existEntity = createDeviceEntity();
		Mockito.when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.ofNullable(existEntity));

		deviceService.register(device);

		Device expectEntity = existEntity;
		expectEntity.getData().add(createWeatherData());

		verify(deviceRepository, times(1)).save(expectEntity);
	}

	@Test
	public void getDeviceCollection_idNotExist_thenReturnNull() {
		String deviceId = "deviceNotExist";
		Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

		Assert.assertNull(deviceService.getDeviceCollection(deviceId, null, null));
	}

	@Test
	public void getDeviceCollection_idExist_thenReturnNotNull() {
		String deviceId = "deviceId";
		Device existEntity = createDeviceEntity();
		Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(existEntity));

		Assert.assertNotNull(deviceService.getDeviceCollection(deviceId, null, null));
	}

	@Test
	public void getDeviceColection_idExist_withStartTime_thenCheckDataSize() {
		String deviceId = "deviceId";
		LocalDateTime startTime = LocalDateTime.of(2021, Month.DECEMBER, 6, 12, 30, 40);
		Device existEntity = createDeviceEntityForTestStartTimeEndTime();
		Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(existEntity));

		DeviceDto deviceCollection = deviceService.getDeviceCollection(deviceId, startTime, null);
		assertEquals(3, deviceCollection.getData().size());
	}

	@Test
	public void getDeviceCollection_idExist_withEndTime_thenCheckDataSize() {
		String deviceId = "deviceId";
		LocalDateTime endTime = LocalDateTime.of(2021, Month.DECEMBER, 6, 12, 30, 40);
		Device existEntity = createDeviceEntityForTestStartTimeEndTime();
		Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(existEntity));

		DeviceDto deviceCollection = deviceService.getDeviceCollection(deviceId, null, endTime);
		assertEquals(3, deviceCollection.getData().size());
	}

	@Test
	public void getDeviceCollction_idExist_withStartTimeEndTime_thenCheckDataSize() {
		String deviceId = "deviceId";
		LocalDateTime startTime = LocalDateTime.of(2015, Month.DECEMBER, 6, 12, 30, 40);
		LocalDateTime endTime = LocalDateTime.of(2021, Month.DECEMBER, 11, 15, 30, 40);
		Device existEntity = createDeviceEntityForTestStartTimeEndTime();
		Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(existEntity));

		DeviceDto deviceCollection = deviceService.getDeviceCollection(deviceId, startTime, endTime);
		assertEquals(4, deviceCollection.getData().size());
	}

	private RegisterDeviceDto createRegisterData() {
		return RegisterDeviceDto.builder().deviceId("deviceTest").latitude(11.2d).longitude(-113.4)
				.data(createWeatherData()).build();
	}

	private Device createDeviceEntityForTestStartTimeEndTime() {
		Temperature temperature = new Temperature("C", "100");
		WeatherData weatherData1 = new WeatherData(100, temperature,
				LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40));
		WeatherData weatherData2 = new WeatherData(100, temperature,
				LocalDateTime.of(2016, Month.JULY, 29, 19, 30, 40));
		WeatherData weatherData3 = new WeatherData(100, temperature,
				LocalDateTime.of(2021, Month.DECEMBER, 6, 12, 30, 40));
		WeatherData weatherData4 = new WeatherData(100, temperature,
				LocalDateTime.of(2021, Month.DECEMBER, 8, 19, 30, 40));
		WeatherData weatherData5 = new WeatherData(100, temperature,
				LocalDateTime.of(2021, Month.DECEMBER, 10, 19, 30, 40));

		List<WeatherData> weatherDatas = new ArrayList<WeatherData>();
		weatherDatas.add(weatherData1);
		weatherDatas.add(weatherData2);
		weatherDatas.add(weatherData3);
		weatherDatas.add(weatherData4);
		weatherDatas.add(weatherData5);

		return Device.builder().deviceId("deviceId").latitude(11.2d).longitude(-113.4).data(weatherDatas).build();
	}

	private Device createDeviceEntity() {
		List<WeatherData> weatherDatas = new ArrayList<WeatherData>();
		weatherDatas.add(createWeatherData());

		return Device.builder().deviceId("deviceTest").latitude(11.2d).longitude(-113.4).data(weatherDatas).build();
	}

	private WeatherData createWeatherData() {
		return new WeatherData(100, createTemperature(), LocalDateTime.now());
	}

	private Temperature createTemperature() {
		return new Temperature("C", "100");
	}

}

package com.augen.api.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.augen.Application;
import com.augen.dto.Temperature;
import com.augen.dto.WeatherData;
import com.augen.dto.request.RegisterDeviceDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeviceControllerIntegrationTest {

	@InjectMocks
	DeviceController deviceController;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MessageSource messageSource;

	@Test
	public void shouldReturnErrorMessageDeviceNotFound() throws Exception {
		mvc.perform(get("/api/devices/notfound").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value(messageSource.getMessage("device.not.found", null, null)));
	}

	@Test
	public void shouldRegisterSuccess() throws Exception {

		RegisterDeviceDto r1 = mockRegisterDevice();
		byte[] r1Json = toJson(r1);

		mvc.perform(post("/api/devices").content(r1Json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void shouldRegisterFaild_DeviceIdIsRequired() throws Exception {

		RegisterDeviceDto r1 = mockRegisterDevice();
		r1.setDeviceId("");
		byte[] r1Json = toJson(r1);

		mvc.perform(post("/api/devices").content(r1Json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message").value(messageSource.getMessage("bad.request.error", null, null)));
	}

	@Test
	public void shouldRegisterFaild_LongTitudeAndlatitudeIsRequired() throws Exception {

		RegisterDeviceDto r1 = mockRegisterDevice();
		r1.setLatitude(null);
		r1.setLongitude(null);
		byte[] r1Json = toJson(r1);

		mvc.perform(post("/api/devices").content(r1Json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message").value(messageSource.getMessage("bad.request.error", null, null)));
	}

	@Test
	public void shouldRegisterFaild_DataIsRequired() throws Exception {

		RegisterDeviceDto r1 = mockRegisterDevice();
		r1.setData(null);
		byte[] r1Json = toJson(r1);

		mvc.perform(post("/api/devices").content(r1Json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message").value(messageSource.getMessage("bad.request.error", null, null)));
	}

	@Test
	public void shouldReturnEmptyDataWhenStartTimeInFuture() throws Exception {
		LocalDateTime startTimeInFuture = LocalDateTime.now().plusMonths(1);

		mvc.perform(get("/api/devices/xyz123?startTime=" + startTimeInFuture).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.deviceInfo.data", hasSize(0)));
	}

	private RegisterDeviceDto mockRegisterDevice() {
		WeatherData data = new WeatherData();
		data.setHumidity(100);
		data.setTemperature(new Temperature("C", "23.5"));

		return RegisterDeviceDto.builder().deviceId("xyz123").latitude(12.3).longitude(-22.3).data(data).build();
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
}

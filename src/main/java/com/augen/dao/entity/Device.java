package com.augen.dao.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.augen.dto.WeatherData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document("device")
@AllArgsConstructor
@Data
@Builder
public class Device {

	@Id
	private String deviceId;
	
	private Double latitude;
	
	private Double longitude;
	
	private List<WeatherData> data;
}

package com.augen.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.augen.dao.entity.Device;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String>{

	@Query("{deviceId:'?0'}")
	List<Device> findItemByDeviceId(String deviceId);
}

package com.solum.fwmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solum.fwmanager.entity.FirmwarePackage;

public interface FirmwarePackageRepository<T extends FirmwarePackage> extends JpaRepository<FirmwarePackage, Long> {

	Optional<T>	findByfwVersion(String fwfwVersion);
	
	Optional<T>	findByTypeAndFwVersion(String type, String fwVersion);
	
}

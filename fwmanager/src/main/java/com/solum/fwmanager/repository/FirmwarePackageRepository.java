package com.solum.fwmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solum.fwmanager.entity.FirmwarePackage;

public interface FirmwarePackageRepository extends JpaRepository<FirmwarePackage, Long> {

	Optional<FirmwarePackage>	findByfwVersion(String fwfwVersion);
	
	Optional<FirmwarePackage>	findByTagTypeAndFwVersion(String type, String fwVersion);
	
}

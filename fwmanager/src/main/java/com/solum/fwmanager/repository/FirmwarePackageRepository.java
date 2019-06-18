package com.solum.fwmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solum.fwmanager.entity.FirmwarePackage;

public interface FirmwarePackageRepository<T extends FirmwarePackage> extends JpaRepository<FirmwarePackage, Long> {

	T	findByfwVersion(String fwfwVersion);
	
}

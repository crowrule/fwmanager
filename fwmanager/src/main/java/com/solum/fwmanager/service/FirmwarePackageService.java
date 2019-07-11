package com.solum.fwmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.repository.FirmwarePackageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirmwarePackageService {

	@Autowired
	FirmwarePackageRepository	firmwarePackageRepository;
	
	public	FirmwarePackageDTO	getFirmwarePackageId(Long id) {
		
		Optional<FirmwarePackage> ret = firmwarePackageRepository.findById(id);
		
		if (!ret.isPresent()) return null;
		
		FirmwarePackageDTO response = new FirmwarePackageDTO(ret.get().getType(),
				ret.get().getAttribute(),
				ret.get().getSiteCode(), 
				ret.get().getJobNumber(), 
				ret.get().getFwVersion(), 
				ret.get().getTagClass(), 
				ret.get().getMode(), 
				ret.get().getFileName());
		
		return response;
		
	}
	
	public	int registerFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		FirmwarePackage fwEntity = firmwarePackage.toEntity();
		int	ret = 0;
		
		Optional<FirmwarePackage> oldFW = firmwarePackageRepository.findByTypeAndFwVersion(
											firmwarePackage.getType(), 
											firmwarePackage.getFwVersion());
		
		// Update the existed record
		if (oldFW.isPresent()) {
			ret = 1;
			fwEntity.setId(oldFW.get().getId());
		}else ret = 2;
		
		Long id = firmwarePackageRepository.saveAndFlush(fwEntity).getId();
		
		log.debug("Save or Update FirmwaerPackage ID : {}", id);
		
		return ret;
		
	}
	
	// TODO : Refine Validation Rule 
	public boolean validateFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		// TODO : is the FW file existed at the place pointed from FilePath.
		
		return true;
	}
	
}

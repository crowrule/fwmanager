package com.solum.fwmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.dto.CommonResponseDTO;
import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.repository.FirmwarePackageRepository;

import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirmwarePackageService {

	@Autowired
	FirmwarePackageRepository	firmwarePackageRepository;
	
	public	FirmwarePackage	getFirmwarePackagebyId(Long id) {
		
		Optional<FirmwarePackage> ret = firmwarePackageRepository.findById(id);
		
		if (!ret.isPresent()) return null;
		else return ret.get();
		
	}
	
	
	public	FirmwarePackageDTO	getFirmwarePackageDTObyId(Long id) {
		
		Optional<FirmwarePackage> ret = firmwarePackageRepository.findById(id);
		
		if (!ret.isPresent()) return null;

		FirmwarePackageDTO response = new FirmwarePackageDTO(ret.get().getTagType(),
				ret.get().getJobNumber(), 
				ret.get().getFwVersion(),
				ret.get().getCompType(),
				ret.get().getCompSize(),
				ret.get().getDecompSize(),
				ret.get().getTagClass(), 
				ret.get().getOtaMode(), 
				ret.get().getFileName());
		
		return response;
		
	}
	
	public	CommonResponseDTO registerFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		FirmwarePackage fwEntity = firmwarePackage.toEntity();
		
		Optional<FirmwarePackage> oldFW = firmwarePackageRepository.findByTypeAndFwVersion(
											firmwarePackage.getTagType(), 
											firmwarePackage.getFwVersion());
		
		int	registerType = 1;
		
		// TODO : Refine the scenario for the existed record upload
		if (oldFW.isPresent()) {
			fwEntity.setId(oldFW.get().getId());
			registerType = 2;
		}
		
		Long id = firmwarePackageRepository.saveAndFlush(fwEntity).getId();
		log.debug("Save or Update FirmwaerPackage ID : {}", id);

		CommonResponseDTO res = new CommonResponseDTO();
		
		res.setResponseCode(id);
		switch(registerType) {
		case 1 : res.setResponseMessage(new StringBuilder("Successfully Update Existed Package for ")
											.append(firmwarePackage.getTagType())
											.append(", version : ").append(firmwarePackage.getFwVersion())
											.toString());
		case 2 : res.setResponseMessage(new StringBuilder("Successfully Register Firmware Package for ")
											.append(firmwarePackage.getTagType())
											.append(", version : ").append(firmwarePackage.getFwVersion())
											.toString());
		}
		
		return res;
		
	}
	
	// TODO : Refine Validation Rule 
	public boolean validateFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		// TODO : is the FW file existed at the place pointed from FilePath.
		
		return true;
	}
	
}

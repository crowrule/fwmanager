package com.solum.fwmanager.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.repository.FirmwarePackageRepository;

@Service
public class FirmwarePackageService {

	@Autowired
	FirmwarePackageRepository<FirmwarePackage>	firmwarePackageRepository;
	
	public	FirmwarePackageDTO	getFirmwarePackageId(Long id) {
		
		Optional<FirmwarePackage> ret = firmwarePackageRepository.findById(id);
		
		if (!ret.isPresent()) return null;
		
		FirmwarePackageDTO response = new FirmwarePackageDTO(ret.get().getType(),
				ret.get().getAttribute(),
				ret.get().getSiteCode3(), 
				ret.get().getJobNumber(), 
				ret.get().getFwVersion(), 
				ret.get().getTagClass(), 
				ret.get().getMode(), 
				ret.get().getFile_Name());
		
		return response;
		
	}
	
	public	Long registerFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		FirmwarePackage newOne = firmwarePackage.toEntity();
		
		Long id = firmwarePackageRepository.saveAndFlush(newOne).getId().getMostSignificantBits();
		
		return id;
		
	}
	
}

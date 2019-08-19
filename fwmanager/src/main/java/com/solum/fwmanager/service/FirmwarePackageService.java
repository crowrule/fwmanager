package com.solum.fwmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.repository.FirmwarePackageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirmwarePackageService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;	
	
	@Autowired
	FirmwarePackageRepository	firmwarePackageRepository;
	
	public	FirmwarePackage	getFirmwarePackagebyId(Long id) {
		
		Optional<FirmwarePackage> ret = firmwarePackageRepository.findById(id);
		
		if (!ret.isPresent()) return null;
		else return ret.get();
		
	}
/*	
	@Deprecated
	@Transactional
	public	CommonResponseDTO registerFirmwarePackage(File uploadFile, FirmwarePackageDTO firmwarePackage) {
		
		FirmwarePackage fwEntity = getFirmwarePackageEntityFromDTO(firmwarePackage);
		
		Optional<FirmwarePackage> oldFW = firmwarePackageRepository.findByTagTypeCodeAndFwVersion(
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

		//
		//	Publish Event
		//
		// TODO : Retrieve Application Tag Type Info from GW Tag Type Info
		//String targetTagType =  
		FirmwarePackageEvent firmwarePackageEvent = new FirmwarePackageEvent(this, fwEntity);
		applicationEventPublisher.publishEvent(firmwarePackageEvent);		
		
		//
		//	Process Response
		//
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

	
	private FirmwarePackage getFirmwarePackageEntityFromDTO(FirmwarePackageDTO dto) {
		
		FirmwarePackage entity = new FirmwarePackage();
		SLabelType	labelType = SLabelType.valueOf(dto.getTagType());
		
		entity.setFileName(dto.getFileName());
		entity.setFwVersion(dto.getFwVersion());
		entity.setJobNumber(dto.getJobNumber());
		
		entity.setTagType(labelType.getHexValue());
		entity.setTagClass(labelType.getClassType().getValue());
		
		if (labelType.getClassType() == ClassType.MARVELL || 
				labelType.getClassType() == ClassType.SEMCO) entity.setOtaMode(0);
		else entity.setOtaMode(dto.getOtaMode());
		
		// TODO : compSize, decompSize, compType 
		entity.setCompType(1);
		entity.setCompSize(10000);
		entity.setDecompSize(20000);
		
		return entity;
	}
*/
}

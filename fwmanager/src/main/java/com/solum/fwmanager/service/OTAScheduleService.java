package com.solum.fwmanager.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.dto.OTAPackageDTO;
import com.solum.fwmanager.dto.OTAScheduleDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.entity.OTASchedule;
import com.solum.fwmanager.repository.OTAScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OTAScheduleService {
	
	@Autowired
	FileStorageService	fileStorageService;
	
	@Autowired
	OTAScheduleRepository	otaScheduleRepository;
	
	public OTAPackageDTO	getOTAScheduleByMac(String mac) {
		
		Optional<OTASchedule>	opOTASchedule = otaScheduleRepository.findById(mac);
		
		// Check whether the OTA schedule is exist or not
		if (!opOTASchedule.isPresent()) {
			return new OTAPackageDTO();
		}
		
		// Check whether the current time is valid. 
		LocalDateTime	currentTime = LocalDateTime.now();
			
		if (opOTASchedule.get().getOtaTime().isBefore(currentTime.minusMinutes(5))
			|| opOTASchedule.get().getOtaTime().isAfter(currentTime.plusMinutes(5))
			|| opOTASchedule.get().getOtaTime().isEqual(currentTime.plusMinutes(5))) {
			return new OTAPackageDTO();
		}

		// Build response DTO for GW
		OTAPackageDTO otaPackageDto;
		try {
			otaPackageDto = convertFromFirmwarePackageEntity(opOTASchedule.get().getFirmwarePackage());
		} catch (IOException e) {
			log.error("Fail to convert 'otaPackageDTO' from firmware package : {}", e.getMessage());
			
			return null;
		}
		
		return otaPackageDto;
	}
	
	public List<OTAScheduleDTO> getAllOTASchedule() {
		List<OTAScheduleDTO> scheduleList = otaScheduleRepository.findAll(Sort.by("stationCode").and(Sort.by("otaTime")))
											.stream()
											.map(entity->OTAScheduleDTO.builder()
													.stationCode(entity.getStationCode())
													.gwIp(entity.getGwIp())
													.gwMac(entity.getGwMac())
													.otaTime(entity.getOtaTime())
													.build())
											.collect(Collectors.toList());				
		
		return scheduleList;
	}
	
	public List<OTAScheduleDTO> getOTAScheduleInfo(String stationCode) {
		List<OTAScheduleDTO> res = otaScheduleRepository.findAllByStationCode(stationCode)
								.stream()
								.map(entity->OTAScheduleDTO.builder()
										.stationCode(entity.getStationCode())
										.gwIp(entity.getGwIp())
										.gwMac(entity.getGwMac())
										.otaTime(entity.getOtaTime())
										.build())
								.collect(Collectors.toList());
		
		return res;
								
	}
	
	private OTAPackageDTO convertFromFirmwarePackageEntity(FirmwarePackage fwPackage) throws IOException {
		OTAPackageDTO otaPackageDto = new OTAPackageDTO();
		
		otaPackageDto.setTagType(fwPackage.getTagTypeCode());
		otaPackageDto.setTagClass(fwPackage.getTagClass());
		otaPackageDto.setJobNumber(fwPackage.getJobNumber());
		otaPackageDto.setFwVer(fwPackage.getFwVersion());
		otaPackageDto.setCompType(fwPackage.getCompType());
		otaPackageDto.setCompSize(fwPackage.getCompSize());
		otaPackageDto.setDecompSize(fwPackage.getDecompSize());
		otaPackageDto.setOtaMode(fwPackage.getOtaMode());
		
		String	binaryData = fileStorageService.getUploadedFileContent(fwPackage.getFileName());
		otaPackageDto.setBinaryData(binaryData);
		
		return otaPackageDto;
	}
}

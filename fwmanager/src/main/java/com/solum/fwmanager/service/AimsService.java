package com.solum.fwmanager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.aims.core.solum.util.SLabelType;
import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.external.AimsRepository;

@Service
public class AimsService {

	@Autowired
	AimsRepository	aimsRepository;
	
	public	List<String> getAllStationList() {
		
		return aimsRepository.getStationList();

	}
	
	public	List<String> getTagTypeList(String stationCode) {
		
		// List<String> tagList = new ArrayList<String>();
		
		if (stationCode.isEmpty()) return aimsRepository.getInstalledAllTagTypeList();
		else return aimsRepository.getInstalledTagTypeListByStation(stationCode);
		

	}
	
	public	List<TargetStationDTO> getTargetStationList(String tagType, String tagTypeName) {
		
		List<String> labelList = Arrays.stream(SLabelType.values())
			.filter(labelType->{
				if (tagTypeName.isEmpty()) return true;
				else if (tagTypeName.equalsIgnoreCase(String.valueOf(labelType))) return true;
				else return false;
			})
			.map(labelType->labelType.getTypeCode()[0])
			.collect(Collectors.toList());
		
		if (labelList.isEmpty()) return new ArrayList<TargetStationDTO>();
		else return aimsRepository.getTargetStationList(labelList);
	}
}

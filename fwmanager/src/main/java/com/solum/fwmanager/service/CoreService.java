package com.solum.fwmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.dto.TagTypeInfoDTO;
import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.external.CoreDao;

@Service
public class CoreService {

	@Autowired
	CoreDao	coreDao;
	
	public	List<TagTypeInfoDTO> getGwCount() {
		return coreDao.getInstalledTagTypeListByStation("ABC");
	}
	
	public	List<TagTypeInfoDTO> getTagTypeList(String stationCode) {
		return coreDao.getInstalledTagTypeListByStation(stationCode);
	}
	
	public	List<TargetStationDTO> getTargetStationList(String tagAttribute) {
		return coreDao.getTargetStationList(tagAttribute);
	}
}

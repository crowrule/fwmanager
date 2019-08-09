package com.solum.fwmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.entity.OTASchedule;
import com.solum.fwmanager.service.CoreService;
import com.solum.fwmanager.service.FirmwarePackageService;
import com.solum.fwmanager.service.OTAScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/")
public class ConceptualUIController {

	@Autowired
	CoreService		coreService;
	
	@Autowired
	OTAScheduleService	otaScheduleService;
	
	@Autowired
	FirmwarePackageService	firmwarePackageService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String fwregister(Model model) throws Exception {

		List<String> tagTypeList =  coreService.getTagTypeList("");
		
		model.addAttribute("tagTypes", tagTypeList);
		
		return "upload";
	}	
	
	@RequestMapping(value = "targetList", method = RequestMethod.GET)
	public String targetList(Model model) throws Exception {
			
		List<OTASchedule> schedulelist =  otaScheduleService.getAllOTASchedule();
		
		model.addAttribute("schedulelist", schedulelist);
			
		return "targetList";
	}
	
	@RequestMapping(value = "stationList", method = RequestMethod.GET)
	public String targetList(Model model,
			@RequestParam(required=false, defaultValue="1") int fwpackageId) throws Exception {
		
		// TODO : Temporary use constant value
		fwpackageId = 1;
		FirmwarePackage fwPackage = firmwarePackageService.getFirmwarePackagebyId((long)fwpackageId);
		log.info("FW Package Info : Tag Type({}), Version({})", fwPackage.getTagType(), fwPackage.getFwVersion());
		List<TargetStationDTO> stationList =  coreService.getTargetStationList("42", "GRAPHIC_2_9_RED_NFC_INT_RT");

		model.addAttribute("stationlist", stationList);
			
		return "stationList";
	}
	
	@RequestMapping(value = "auto", method = RequestMethod.GET)
	public String autoReservation(Model model) throws Exception {
		return "auto";
	}
	
}

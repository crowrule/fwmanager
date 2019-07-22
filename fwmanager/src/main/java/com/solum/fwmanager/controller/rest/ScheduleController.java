package com.solum.fwmanager.controller.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.fwmanager.dto.OTAStationScheduleDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.service.FirmwarePackageService;
import com.solum.fwmanager.service.ScheduleArrangeService;
import com.solum.fwmanager.service.ScheduleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value= "/api")
public class ScheduleController {

	@Autowired
	FirmwarePackageService	firmwarePackageService;	
	
	@Autowired
	ScheduleArrangeService	scheduleArrangeService;
	
	@Autowired
	ScheduleService	scheduleService;
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@PostMapping("/schedulearrangebyfw/{packageId}")
	public ResponseEntity<List<OTAStationScheduleDTO>> arrangeOTASchedule(
			@PathVariable String packageId
			, @RequestBody List<String> stationList){
		
		// TODO ; The following 
		// FirmwarePackage relatedPackage = firmwarePackageService.getFirmwarePackagebyId(packageId);
		
		List<OTAStationScheduleDTO> res = stationList.stream().map(stationCode->{
			return scheduleArrangeService.setAutoArrangeOTASchedule(stationCode);
			
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(res);
		
	}
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@GetMapping("/otaschedule/{mac}")
	public ResponseEntity<String> getOTAScheduleFromGWbyMac(@PathVariable String mac){
		
		// TODO ; The following 
		LocalDateTime	ret =  scheduleService.getOTAScheduleByMac(mac);
		
		
		return ResponseEntity.ok(ret.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	
	/*
	@ApiOperation(tags={"OTA Schedule"}, value="Get Schedule List(Test)")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@GetMapping("/schedulelist")
	public ResponseEntity<List<LocalDateTime>> getScheudleList(int count){
		
		List<LocalDateTime> list = scheduleArrangeService.setAutoArrangeOTASchedule(count);
		
		return ResponseEntity.ok(list);
		
	}
	*/
}

package com.solum.fwmanager.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.fwmanager.dto.OTAPackageDTO;
import com.solum.fwmanager.dto.OTAScheduleDTO;
import com.solum.fwmanager.dto.OTAStationScheduleDTO;
import com.solum.fwmanager.service.AimsService;
import com.solum.fwmanager.service.OTAScheduleService;
import com.solum.fwmanager.service.ScheduleArrangeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value= "/api")
public class ScheduleController {
	
	@Autowired
	ScheduleArrangeService	scheduleArrangeService;
	
	@Autowired
	OTAScheduleService	otaScheduleService;
	
	@Autowired
	AimsService	aimsService;
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@PostMapping("/arrangeschedulebyfw/{packageId}")
	public ResponseEntity<List<OTAStationScheduleDTO>> arrangeOTASchedule(
			@PathVariable String packageId
			, @RequestBody List<String> stationList){

		if (stationList == null || stationList.isEmpty()) {
			log.info("No Target Station. Switch to all stations for Firmware Update.");
			stationList = aimsService.getAllStationList();
		}
		
		List<OTAStationScheduleDTO> res = stationList.stream().map(stationCode->{

			return scheduleArrangeService.setAutoArrangeOTASchedule(packageId, stationCode);

		}).filter(dto->(dto!=null))
		.collect(Collectors.toList());
		
		return ResponseEntity.ok(res);
		
	}
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved."),
			@ApiResponse(code = 204, message = "No OTA Scheudle")
			})
	@GetMapping("/otaschedulelist/{stationCode}")
	public ResponseEntity<List<OTAScheduleDTO>> retrieveOTAScheduleByStaiton(
			@PathVariable String stationCode){

		List<OTAScheduleDTO> res = otaScheduleService.getOTAScheduleInfo(stationCode);
		
		if (res.isEmpty()) return ResponseEntity.noContent().build();
		else return ResponseEntity.ok(res);
		
	}
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved."),
			@ApiResponse(code = 204, message = "No OTA Scheudle")
			})
	@GetMapping("/otaschedulelistall")
	public ResponseEntity<List<OTAScheduleDTO>> retrieveAllOTAScheduleByStaiton(){

		List<OTAScheduleDTO> res = otaScheduleService.getAllOTASchedule();
		
		if (res.isEmpty()) return ResponseEntity.noContent().build();
		else return ResponseEntity.ok(res);
		
	}
	
	@ApiOperation(tags={"OTA Schedule"}, value="Arrange OTA Schedule for multiple stations")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Exist a adequate OTA package"),
			@ApiResponse(code = 204, message = "No OTA package"),
			@ApiResponse(code = 500, message = "Internal Server Error")
			})
	@GetMapping("/otaschedulebygw/{mac}")
	public ResponseEntity<OTAPackageDTO> getOTAScheduleFromGWbyMac(@PathVariable String mac){
		
		OTAPackageDTO	ret =  otaScheduleService.getOTAScheduleByMac(mac);
		
		if (ret == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		else if (ret.getCompSize() == 0) return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(ret);
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

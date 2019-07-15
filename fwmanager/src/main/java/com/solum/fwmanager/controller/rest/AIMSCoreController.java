package com.solum.fwmanager.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.fwmanager.dto.TagTypeInfoDTO;
import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.service.CoreService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value= "/api")
public class AIMSCoreController {
	
	@Autowired
	CoreService	coreService;
	
	@ApiOperation(tags={"AIMS"}, value="Retrieve Tag Type Info")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 500, message = "Internal.")
			})
	@GetMapping(value = "tagtype", produces="application/json")
	public ResponseEntity<List<TagTypeInfoDTO>> getTagInfoByStaion(String stationCode) throws Exception {
		List<TagTypeInfoDTO> ret =  coreService.getTagTypeList(stationCode);
		
		return ResponseEntity.ok(ret);
	}
	
	@ApiOperation(tags={"AIMS"}, value="Retrieve target station list")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 500, message = "Internal.")
			})
	@GetMapping(value = "stationlist/{tagattribute:.+}", produces="application/json")
	public ResponseEntity<List<TargetStationDTO>> getTargetStationList(
				@PathVariable("tagattribute") String tagattribute
			) throws Exception {
		List<TargetStationDTO> ret =  coreService.getTargetStationList(tagattribute);
		
		return ResponseEntity.ok(ret);
	}
}

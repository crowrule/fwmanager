package com.solum.fwmanager.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@ApiOperation(tags={"AIMS"}, value="Retrieve List of Types of Registered Tags ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 500, message = "Internal.")
			})
	@GetMapping(value = "tagtypelist", produces="application/json")
	public ResponseEntity<List<String>> getTagTypeList() throws Exception {
		List<String> ret =  coreService.getTagTypeList("");
		
		return ResponseEntity.ok(ret);
	}
	
	@ApiOperation(tags={"AIMS"}, value="Retrieve target station list by uploaded firmware package")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 500, message = "Internal.")
			})
	@GetMapping(value = "stationlist", produces="application/json")
	public ResponseEntity<List<TargetStationDTO>> getTargetStationList(
				@RequestParam(value="tagtype", required=true) String tagtype,
				@RequestParam("tagtypename") String tagtypename
			) throws Exception {

		List<TargetStationDTO> ret =  coreService.getTargetStationList(tagtype, tagtypename);
		
		return ResponseEntity.ok(ret);
	}
}

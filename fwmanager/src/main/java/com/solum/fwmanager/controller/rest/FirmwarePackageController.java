package com.solum.fwmanager.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.fwmanager.dto.CommonResponseDTO;
import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.service.FirmwarePackageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value= "/api")
public class FirmwarePackageController {

	@Autowired
	FirmwarePackageService	firmwarePackageService;
	
	@ApiOperation(tags={"Package Management"}, value="Register a Update Package")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@PostMapping("/registerota")
	public ResponseEntity<CommonResponseDTO> registerReservation(@RequestBody FirmwarePackageDTO reqParam){
		log.info("register firmware");
		
		CommonResponseDTO	res = new CommonResponseDTO();
		
		// TODO : Create Super-Class which generate common 405 Error Message.
		if (!firmwarePackageService.validateFirmwarePackage(reqParam)) {
			
			res.setResponseCode(-1);
			res.setResponseMessage("Invalid Parameter");
			
			return ResponseEntity.badRequest().body(res); 
		}
		
		res  = firmwarePackageService.registerFirmwarePackage(reqParam);
		 
		return ResponseEntity.ok(res);

	}

	
}

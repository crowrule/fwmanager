package com.solum.fwmanager.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.service.FirmwarePackageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value= {"/api"}, produces="application/json")
public class FirmwarePackageController {

	@Autowired
	FirmwarePackageService	firmwarePackageService;
	
	@ApiOperation(tags={"Package Management"}, value="Register a Update Package")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@PostMapping("/register")
	public ResponseEntity<String> registerReservation(@RequestBody FirmwarePackageDTO reqParam){
		log.info("register firmware");
		
		// TODO : Create Super-Class which generate common 405 Error Message.
		if (firmwarePackageService.validateFirmwarePackage(reqParam)) return ResponseEntity.badRequest().body("Invalid Parameter"); 
		
		int	result  = firmwarePackageService.registerFirmwarePackage(reqParam);
		
		switch(result) {
		case 1 : return ResponseEntity.ok(new StringBuilder("Successfully Update Existed Package for ")
											.append(reqParam.getType())
											.append(", version : ").append(reqParam.getFwVersion())
											.toString());
		case 2 : return ResponseEntity.ok(new StringBuilder("Successfully Register Firmware Package for ")
											.append(reqParam.getType())
											.append(", version : ").append(reqParam.getFwVersion())
											.toString());
		default : return ResponseEntity.badRequest().body("Invalid Parameter"); 
		}

	}
	
}

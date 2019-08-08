package com.solum.fwmanager.controller.rest;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solum.fwmanager.dto.CommonResponseDTO;
import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.service.FileStorageService;
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
	private FirmwarePackageService	firmwarePackageService;
	
    @Autowired
    private FileStorageService fileStorageService;	
	
    @Deprecated
	@ApiOperation(tags={"Package Management"}, value="Register a Update Package")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
	@PostMapping("/registerota")
	public ResponseEntity<CommonResponseDTO> registerReservation(
			@RequestPart(required=true) FirmwarePackageDTO reqParam,
			@RequestPart(required=true) MultipartFile filePath
			){
		log.info("register firmware");
		
		CommonResponseDTO	res = new CommonResponseDTO();
		
		FirmwarePackageDTO uploadResult = fileStorageService.storeFile(filePath);
    	
    	if (uploadResult == null) {
    		return ResponseEntity
    				.status(HttpStatus.METHOD_NOT_ALLOWED)
    				.body(new CommonResponseDTO(-2, "Fail to upload package")
    				);
    	}
		
		// TODO : Create Super-Class which generate common 405 Error Message.
		if (!firmwarePackageService.validateFirmwarePackage(reqParam)) {
			
			res.setResponseCode(-1);
			res.setResponseMessage("Invalid Parameter");
			
			return ResponseEntity.badRequest().body(res); 
		}
		
		//res  = firmwarePackageService.registerFirmwarePackage(new File(), uploadResult);
		 
		return ResponseEntity.ok(res);

	}

	
}

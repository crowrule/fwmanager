package com.solum.fwmanager.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.solum.fwmanager.dto.CommonResponseDTO;
import com.solum.fwmanager.dto.FirmwarePackageDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.repository.FirmwarePackageRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageService {

	@Autowired
	FirmwarePackageRepository	firmwarePackageRepository;	
	
    private final Path fileStorageLocation;
    
    // TODO : Extract storage URL to property & rebuild it by tag type info 
    // TODO : Refine Configure
    private final String uploadUrl = "/home/dohoon/Public";
    private final String prefix = "without_header.";
    
	public FileStorageService() {
    	this.fileStorageLocation = Paths.get(uploadUrl).toAbsolutePath().normalize();

		try {
		    Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			// TODO : Refine Exception
		    // throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
			ex.printStackTrace();
		}
	}
	
	public CommonResponseDTO storeFile(MultipartFile file) {
		
		log.warn("There is NOT tagtype information.");
		
		return storeFile("", file);
	}

	public CommonResponseDTO storeFile(String tagType, MultipartFile file) {
		
		// Normalize file name
		String 	fileName = StringUtils.cleanPath(file.getOriginalFilename());
		long	originalSize = file.getSize();
		
		try {
			// TODO : To validate File Name 
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				//throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
				//throw new Exception("AAA");
				
				log.warn("Invalid File Name : {}", fileName);
				return null;
			}
			
			// Parsing
			OtaFileHeader header = parseOTAPackageFileHeader(file.getInputStream());
			FirmwarePackage	fwpackage = header.extractFirmwarePackageEntity();

			// TODO : Refine the following parameter
			fwpackage.setJobNumber(100);
			fwpackage.setCompSize((short)0);
			
			// Write ota data without header
			String otaData = Base64.getEncoder().encodeToString(
								Arrays.copyOfRange(IOUtils.toByteArray(file.getInputStream())
								, 32
								, (int)originalSize));
			String	encodedfileName = new StringBuilder(prefix).append(fileName).toString();
			Path targetLocation = this.fileStorageLocation.resolve(encodedfileName);
			long copySize = Files.copy(IOUtils.toInputStream(otaData, StandardCharsets.UTF_8)
									, targetLocation
									, StandardCopyOption.REPLACE_EXISTING);
			fwpackage.setCompSize((int)copySize);
			fwpackage.setDecompSize((int)copySize);
			fwpackage.setFileName(encodedfileName);
			fwpackage.setTagType(tagType);
			
			Optional<FirmwarePackage> oldFW = firmwarePackageRepository.findByTagTypeCodeAndFwVersion(
					fwpackage.getTagTypeCode(), 
					fwpackage.getFwVersion());

			int	registerType = 1;

			// TODO : Refine the scenario for the existed record upload
			if (oldFW.isPresent()) {
				fwpackage.setId(oldFW.get().getId());
				registerType = 2;
			}

			Long id = firmwarePackageRepository.saveAndFlush(fwpackage).getId();
			log.debug("Save or Update FirmwaerPackage ID : {}", id);
			
			//
			//	Process Response
			//
			CommonResponseDTO res = new CommonResponseDTO();
			res.setResponseCode(id);
			switch(registerType) {
			case 1 : res.setResponseMessage(new StringBuilder("Successfully Update Existed Package for ")
												.append(fwpackage.getTagType())
												.append(", version : ").append(fwpackage.getFwVersion())
												.toString());
			case 2 : res.setResponseMessage(new StringBuilder("Successfully Register Firmware Package for ")
												.append(fwpackage.getTagType())
												.append(", version : ").append(fwpackage.getFwVersion())
												.toString());
			}
			
			return res;
			
		} catch (Exception ex) {
			//throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			ex.printStackTrace();
			
			log.error("Faile to upload file : {}", fileName);
			
			return null;
		}
	}

	/*
    public Resource loadFileAsResource(String fileName) throws Exception {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
			    return resource;
			} else {
			    //throw new MyFileNotFoundException("File not found " + fileName);
				throw new Exception("File Not Found");
				
			}
		} catch (MalformedURLException ex) {
			throw new Exception("File Not Found");
		}
    }
    */
	
	// TODO : Refine Validation Rule 
	public boolean validateFirmwarePackage(FirmwarePackageDTO firmwarePackage) {
		
		// TODO : is the FW file existed at the place pointed from FilePath.
		
		return true;
	}
	
	private OtaFileHeader parseOTAPackageFileHeader(InputStream uploadedFileStream) {
		
		OtaFileHeader	header = new OtaFileHeader();

		byte[] bytes;
		try {
			bytes = IOUtils.toByteArray(uploadedFileStream);

			log.debug("----------Parse Uploaded File--------------------");
			log.debug(">>> Firmware Class : {}", String.format("%x", bytes[0]));
			log.debug(">>> OTA Mode : {}", String.format("%x", bytes[1]));
			log.debug(">>> Tag Type : {}", String.format("%x", bytes[2]));
			log.debug(">>> Version : {}", String.format("%x", bytes[3]));
			log.debug(">>> Site Code : {}", String.format("%x%x", bytes[4], bytes[5]));
			log.debug(">>> Attribute : {}", String.format("%x%x%x%x", bytes[6], bytes[7], bytes[8], bytes[9]));
			log.debug("-------------------------------------------------");

			header.setTagClass(String.format("%x", bytes[0]));
			header.setOtaMode(String.format("%x", bytes[1]));
			header.setTagType(String.format("%x", bytes[2]));
			header.setVersion(String.format("%x", bytes[3]));
			header.setSiteCode(String.format("%x%x", bytes[4], bytes[5]));
			header.setAttribute(String.format("%x%x%x%x", bytes[6], bytes[7], bytes[8], bytes[9]));
			
			return header;
		} catch (IOException e) {
			log.error("-------------------------------------------------");
			log.error("Fail to parse uploaded file : {}", e.getMessage());
			log.error("-------------------------------------------------");
			
			return null;
		}
	}
	
	@NoArgsConstructor
	@Getter @Setter
	private class OtaFileHeader {
		
		String	tagClass;
		
		String	otaMode;
		
		String	tagType;
		
		String	version;		
		
		String	siteCode;
		
		String	attribute;
		
		public FirmwarePackage extractFirmwarePackageEntity() {
			
			FirmwarePackage fwEntity = new FirmwarePackage();
			
			try {
				fwEntity.setTagClass(Integer.valueOf(this.tagClass));
				fwEntity.setOtaMode(Integer.valueOf(this.otaMode));
				fwEntity.setTagType(this.tagType);
				fwEntity.setFwVersion(Integer.valueOf(this.version));
				//fwEntity.setJobNumber(Integer.valueOf(this.attribute));
				
				return fwEntity;
				
			}catch(Exception e) {
				log.error("Fail to extract FirmwarePackage Entity from File Header Information");
				
				return fwEntity;
			}

		}
		
	}
}

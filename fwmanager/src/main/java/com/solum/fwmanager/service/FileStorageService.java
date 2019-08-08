package com.solum.fwmanager.service;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.solum.fwmanager.dto.FirmwarePackageDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;
    
    // TODO : Extract storage URL to property & rebuild it by tag type info
    private final String uploadUrl = "/home/dohoon/Public";

    //@Autowired
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


	public FirmwarePackageDTO storeFile(MultipartFile file) {
		
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
			byte[] bytes = IOUtils.toByteArray(file.getInputStream());
			FirmwarePackageDTO	resDTO = new FirmwarePackageDTO();

			log.info(">>> Firmware Class : {}", String.format("%x", bytes[0]));
			log.info(">>> OTA Mode : {}", String.format("%x", bytes[1]));
			log.info(">>> Tag Type : {}", String.format("%x", bytes[2]));
			log.info(">>> Version : {}", String.format("%x", bytes[3]));
			log.info(">>> Site Code : {}", String.format("%x%x", bytes[4], bytes[5]));
			log.info(">>> Attribute : {}", String.format("%x%x%x%x", bytes[6], bytes[7], bytes[8], bytes[9]));
			
			resDTO.setTagClass((short)bytes[0]);
			resDTO.setOtaMode((short)bytes[1]);
			resDTO.setTagType(String.format("%x", bytes[2]));
			resDTO.setFwVersion((int)bytes[3]);
			resDTO.setSiteCode(String.format("%x%x", bytes[4], bytes[5]));
			
			// TODO : Refine the following parameter
			resDTO.setJobNumber(100);
			resDTO.setCompType((short)0);
			
			String otaData = Base64.getEncoder().encodeToString(Arrays.copyOfRange(bytes, 32, (int)originalSize));
			
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			long copySize = Files.copy(IOUtils.toInputStream(otaData, StandardCharsets.UTF_8), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			resDTO.setCompSize((int)copySize);
			resDTO.setDecompSize((int)copySize);

			
			return resDTO;
			
		} catch (Exception ex) {
			//throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			ex.printStackTrace();
			
			log.error("Faile to upload file : {}", fileName);
			
			return null;
		}
	}

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
}

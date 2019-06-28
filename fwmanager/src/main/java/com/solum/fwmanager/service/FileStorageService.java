package com.solum.fwmanager.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

	// TODO : Extend return code.
	public boolean storeFile(MultipartFile file) {
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
				return false;
			}
			
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			long copySize = Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			//	Uploaded file is different from original file
			if (originalSize != copySize) return false;
			
			return true;
			
		} catch (Exception ex) {
			//throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			ex.printStackTrace();
			
			log.error("Faile to upload file : {}", fileName);
			
			return false;
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

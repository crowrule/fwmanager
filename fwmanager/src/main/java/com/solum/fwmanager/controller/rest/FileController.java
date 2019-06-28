package com.solum.fwmanager.controller.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.solum.fwmanager.controller.param.FileUploadResponse;
import com.solum.fwmanager.service.FileStorageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileController {
	
    @Autowired
    private FileStorageService fileStorageService;
    
	@ApiOperation(tags={"File Management"}, value="Register a Update Package")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully reserved."),
			@ApiResponse(code = 400, message = "Use wrong parameter.")
			})
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
    		@RequestParam("filePath") MultipartFile filePath) {
    	
    	boolean uploadResult = fileStorageService.storeFile(filePath);
    	
    	if (!uploadResult) {
    		return ResponseEntity
    				.status(HttpStatus.METHOD_NOT_ALLOWED)
    				.body(FileUploadResponse
    						.builder()
    						.resultMessage("Fail to upload file")
    						.build()
    				);
    	}

    	// TODO : Make valid URL of uploaded File for each Tag Type
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(filePath.getOriginalFilename())
                .toUriString();
 
        return ResponseEntity
        		.ok(FileUploadResponse
        				.builder()
        				.fileName(filePath.getOriginalFilename())
        				.fileUri(fileDownloadUri)
        				.fileType(filePath.getContentType())
        				.size(filePath.getSize())
        				.resultMessage("Upload a file successfully.")
        				.build()
        			);
       				
    }

	/*
    @PostMapping("/multiUpload")
    public List<UploadFileResponse> uploadMultipleFiles(
    		@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
    		@PathVariable String fileName, HttpServletRequest request) throws Exception {
    	
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        	log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    */
}

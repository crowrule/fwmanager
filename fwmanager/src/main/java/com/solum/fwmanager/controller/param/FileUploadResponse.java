package com.solum.fwmanager.controller.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FileUploadResponse {

    private String fileName;
    private String fileUri;
    private String fileType;
    private long size;	
    private String resultMessage;
    
    @Builder
    public FileUploadResponse(String fileName, String fileUri, String fileType, long size, String resultMessage) {
    	this.fileName = fileName;
    	this.fileUri = fileUri;
    	this.fileType = fileType;
    	this.size = size;
    	this.resultMessage = resultMessage;
    }
}

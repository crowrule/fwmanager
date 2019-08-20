package com.solum.fwmanager.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class OTAPackageDTO {

	private	String	jobNumber;

	private	int	fwVer;
	
	private String	tagType;	
	
	private int	compType;
	
	private int	compSize = 0;
	
	private int decompSize = 0;
	
	private int	tagClass;
	
	private int	otaMode;
	
	@Setter(AccessLevel.NONE)
	private char checkSum;
	
	private String	binaryData;
	
	public void setBinaryData(String binaryData) {
		this.binaryData = binaryData;
		
		byte checkSum = 0x00;	
		byte[] buffers = binaryData.getBytes();
		for(byte single : buffers) checkSum ^= single;
		
		this.checkSum = (char)checkSum;
	}
}

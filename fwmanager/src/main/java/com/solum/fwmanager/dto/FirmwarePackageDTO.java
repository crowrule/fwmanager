package com.solum.fwmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solum.fwmanager.entity.FirmwarePackage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FirmwarePackageDTO {

	// TODO : Check how to retrieve these information
	//@ApiModelProperty
	//private String	type;
	
	@ApiModelProperty(value="2 digits to identify tag type")
	private String	tagType;
	
	//@ApiModelProperty(value="4 digits to access tag")
	//private	String	siteCode;
	
	// TODO : Check how to retrieve these information
	@ApiModelProperty
	private	int	jobNumber;
	
	@ApiModelProperty
	private	String	fwVersion;
	
	@ApiModelProperty
	private short compType;
	
	@ApiModelProperty
	private int	compSize;
	
	@ApiModelProperty
	private int decompSize;
	
	@ApiModelProperty
	private short	tagClass;
	
	@ApiModelProperty
	private short	otaMode;
	
	@ApiModelProperty
	private	String	fileName;
	
	@JsonIgnore
	public FirmwarePackage toEntity() {
		return FirmwarePackage.builder()
				.tagType(tagType)
				.jobNumber(jobNumber)
				.fwVersion(fwVersion)
				.compType(compType)
				.compSize(compSize)
				.decompSize(decompSize)
				.tagClass(tagClass)
				.otaMode(otaMode)
				.fileName(fileName)
				.build();
	}
	
	
	@JsonIgnore
	public boolean equals(Object o) { 
		FirmwarePackageDTO r2 = (FirmwarePackageDTO)o; 
		
		if (((this.tagType == null && r2.getTagType() == null) || this.tagType.equals(r2.getTagType()))
			&& ((this.fwVersion == null && r2.getFwVersion() == null) || this.fwVersion.equals(r2.getFwVersion()))
			&& (this.jobNumber == r2.getJobNumber())
			&& (this.compType == r2.getCompType())
			&& (this.compSize == r2.getCompSize())
			&& (this.decompSize == r2.getDecompSize())
			&& (this.tagClass == r2.getTagClass())
			&& (this.otaMode == r2.getOtaMode())
			&& ((this.fileName == null && r2.getFileName() == null) || this.fileName.equals(r2.getFileName()))
		) return true;
			
		return false;
		
	}
	
}

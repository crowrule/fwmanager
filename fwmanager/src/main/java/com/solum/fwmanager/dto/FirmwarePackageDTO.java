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

	@ApiModelProperty
	private String	type;
	
	@ApiModelProperty
	private String	attribute;
	
	@ApiModelProperty
	private	String	siteCode;
	
	@ApiModelProperty
	private	int	jobNumber;
	
	@ApiModelProperty
	private	String	fwVersion;
	
	@ApiModelProperty
	private short	tagClass;
	
	@ApiModelProperty
	private short	mode;
	
	@ApiModelProperty
	private	String	fileName;
	
	@JsonIgnore
	public FirmwarePackage toEntity() {
		return FirmwarePackage.builder()
				.type(type)
				.attribute(attribute)
				.siteCode3(siteCode)
				.jobNumber(jobNumber)
				.fwVersion(fwVersion)
				.tagClass(tagClass)
				.mode(mode)
				.file_Name(fileName)
				.build();
	}
	
	@JsonIgnore
	public boolean equals(Object o) { 
		FirmwarePackageDTO r2 = (FirmwarePackageDTO)o; 
		
		if (((this.type == null && r2.getType() == null) || this.type.equals(r2.getType()))
			&& ((this.attribute == null && r2.getAttribute() == null) || this.attribute.equals(r2.getAttribute()))
			&& ((this.siteCode == null && r2.getSiteCode() == null) || this.siteCode.equals(r2.getSiteCode()))
			&& ((this.fwVersion == null && r2.getFwVersion() == null) || this.fwVersion.equals(r2.getFwVersion()))
			&& (this.jobNumber == r2.getJobNumber())
			&& (this.tagClass == r2.getTagClass())
			&& (this.mode == r2.getMode())
			&& ((this.fileName == null && r2.getFileName() == null) || this.fileName.equals(r2.getFileName()))
		) return true;
			
		return false;
		
	}
	
}

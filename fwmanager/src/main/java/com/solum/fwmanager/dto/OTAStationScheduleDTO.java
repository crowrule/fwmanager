package com.solum.fwmanager.dto;

import java.time.LocalDateTime;

import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.entity.FirmwarePackage.FirmwarePackageBuilder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class OTAStationScheduleDTO {

	@ApiModelProperty
	String	code;
	
	@ApiModelProperty
	String	name; 
	
	@ApiModelProperty
	int	gwCount;
	
	@ApiModelProperty
	LocalDateTime	firstOTATime;
	
	@ApiModelProperty
	LocalDateTime	lastOTATime;	
	
	@Builder
	public OTAStationScheduleDTO(String code, String name, int gwCount, LocalDateTime first, LocalDateTime last) {
		this.code = code;
		this.name = name;
		this.gwCount = gwCount;
		this.firstOTATime = first;
		this.lastOTATime = last;
	}
}

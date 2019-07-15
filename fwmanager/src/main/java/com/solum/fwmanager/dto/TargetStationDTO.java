package com.solum.fwmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TargetStationDTO {

	@ApiModelProperty
	String	code;
	
	@ApiModelProperty
	String	name; 
	
	@ApiModelProperty
	String	description;
	
	@ApiModelProperty
	int	gwCount;
	
	@ApiModelProperty
	long	tagCount;
	
}

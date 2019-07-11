package com.solum.fwmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TagTypeInfoDTO {
	
	@ApiModelProperty
	String	size;
	
	@ApiModelProperty
	String	type;
	
	@ApiModelProperty
	String	chipset;
	
	@ApiModelProperty
	String	color;
	
	@ApiModelProperty
	boolean	isNfc;
	
	@ApiModelProperty
	String attribute;
}

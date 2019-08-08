package com.solum.fwmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CommonResponseDTO {

	@ApiModelProperty
	private long responseCode;
	
	@ApiModelProperty
	private String responseMessage;
	
}

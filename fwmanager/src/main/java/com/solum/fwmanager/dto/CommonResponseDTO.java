package com.solum.fwmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CommonResponseDTO {

	@ApiModelProperty
	private long responseCode;
	
	@ApiModelProperty
	private String responseMessage;
	
}

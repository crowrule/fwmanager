package com.solum.fwmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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

	//@ApiModelProperty
	//boolean	isNfc;
	
	@JsonIgnore
	String	slabelType = "";
	
	@JsonIgnore
	int width = 0;

	@ApiModelProperty
	boolean	isNfc;
	
	@JsonIgnore
	boolean	isTi;
	
	@ApiModelProperty
	String attribute;
	
	@JsonIgnore
	public void setPropertiesFromLowData() {
		
		if (slabelType.contains("MULTI_SEGMENT")) type = "MULTI_SEGMENT";
		else if (slabelType.contains("7SEGMENT")) type = "7SEGMENT";
		else type="GRAPHIC";
		
		if (isTi) chipset="TI";
		else chipset="243 or Marvell";
		
		if (slabelType.contains("RED")) color="Red";
		else if (slabelType.contains("YEL")) color="Yellow";
		else if (type.equalsIgnoreCase("GRAPHIC")) color="B/W";
		else color="---";
		
		switch(width){
		// TODO
		case 152 : size="1.6"; break;
		case 200 : size="1.6 HD"; break;
		case 212 : size ="2.2"; break;
		// TODO
		case 296 : size="2.9"; break;
		case 264 : size="2.7"; break;
		case 300 : size="3.3"; break;
		case 400 : size="4.2"; break;
		// TODO
		case 600 : size="6.0"; break;
		case 384 : size="7.5"; break;
		case 528 : size="7.5 HD"; break;
		case 640 : size="11.6"; break;
		case 1200 : size="13.3"; break;
		
		default : size = "---";
		
		}
	}
	
}

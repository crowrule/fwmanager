package com.solum.fwmanager.external.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class OTATargetGateway {

	Long	id;
	
	String	macAddress;
	
	String	ipAddress;
}

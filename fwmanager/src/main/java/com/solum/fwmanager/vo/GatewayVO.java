package com.solum.fwmanager.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GatewayVO {

	String	stationCode;
	
	String	name;
	
	String	ip;
	
	String 	mac;
	
	String	status;
	
	String	reserveTime;
	
	@Builder
	public GatewayVO(String stationCode, String name, String ip, String mac, String status, String reserveTime) {
		this.stationCode = stationCode;
		this.name = name;
		this.ip = ip;
		this.mac = mac;
		this.status = status;
		this.reserveTime = reserveTime;
	}
}

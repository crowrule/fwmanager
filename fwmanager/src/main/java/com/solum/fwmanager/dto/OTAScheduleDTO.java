package com.solum.fwmanager.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class OTAScheduleDTO {

	private String	stationCode;
	
	private String	gwIp;
	
	private String gwMac;
	
	private LocalDateTime	otaTime;
	
	@Builder
	public OTAScheduleDTO(String stationCode, String gwIp, String gwMac, LocalDateTime otaTime) {
		this.stationCode = stationCode;
		this.gwIp = gwIp;
		this.gwMac = gwMac;
		this.otaTime = otaTime;
	}
}

package com.solum.fwmanager.event;

import org.springframework.context.ApplicationEvent;

import com.solum.fwmanager.entity.FirmwarePackage;

import lombok.Getter;

@Getter
public class FirmwarePackageEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FirmwarePackage firmwarePackageInfo;
	
	public FirmwarePackageEvent(Object source, FirmwarePackage firmwarePackageInfo) {
		super(source);
		// TODO Auto-generated constructor stub
		
		this.firmwarePackageInfo = firmwarePackageInfo;
	}

}

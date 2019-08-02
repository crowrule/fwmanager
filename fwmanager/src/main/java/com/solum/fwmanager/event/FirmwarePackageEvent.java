package com.solum.fwmanager.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class FirmwarePackageEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String targetTagType;
	
	public FirmwarePackageEvent(Object source, String targetTagType) {
		super(source);
		// TODO Auto-generated constructor stub
		
		this.targetTagType = targetTagType;
	}

}

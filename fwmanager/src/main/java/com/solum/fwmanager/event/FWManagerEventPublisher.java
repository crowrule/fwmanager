package com.solum.fwmanager.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FWManagerEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
 
	public void publishFirmwarePackageEvent(final String message) {
		log.info("Publishing custom event. ");

		FirmwarePackageEvent customSpringEvent = new FirmwarePackageEvent(this, message);
		applicationEventPublisher.publishEvent(customSpringEvent);
	}	
}

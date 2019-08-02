package com.solum.fwmanager.event;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.solum.fwmanager.dto.OTAStationScheduleDTO;
import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.service.CoreService;
import com.solum.fwmanager.service.ScheduleArrangeService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FWManagerEventEventListener {

	@Autowired
	ScheduleArrangeService	scheduleArrangeService;
	
	@Autowired
	CoreService	coreService;
	
	@Async	
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes=FirmwarePackageEvent.class)
    public void handleFirmwarePackageEvent(FirmwarePackageEvent event) {
    	
		log.info("Start to arrange OTA Schedule for TagType : {}", event.getTargetTagType());
		
		List<TargetStationDTO> targetStationList =  coreService.getTargetStationList(event.getTargetTagType());
		
		targetStationList.stream().forEach(stationDTO->{
			String 	stationCode = stationDTO.getCode();
			String	stationName = stationDTO.getName();
			
			
			OTAStationScheduleDTO ret = scheduleArrangeService.setAutoArrangeOTASchedule(stationCode);
			
			log.info("Start to arrage for {}({})", stationName, stationCode);
			log.info("-- Detail Information : GW Count({}) From {} To {}"
					, ret.getGwCount()
					, ret.getFirstOTATime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
					, ret.getLastOTATime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		});
    	
		log.info("Complete to arrange OTA Schedule for TagType : {}", event.getTargetTagType());
    	
    }

}

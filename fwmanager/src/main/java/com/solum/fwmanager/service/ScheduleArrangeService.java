package com.solum.fwmanager.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solum.fwmanager.dto.OTAStationScheduleDTO;
import com.solum.fwmanager.entity.FirmwarePackage;
import com.solum.fwmanager.entity.OTASchedule;
import com.solum.fwmanager.external.AimsRepository;
import com.solum.fwmanager.external.entity.OTATargetGateway;
import com.solum.fwmanager.repository.FirmwarePackageRepository;
import com.solum.fwmanager.repository.OTAScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleArrangeService {

	@Value("${ota.execute.duration.starthour}")
	private int	startHour;
	
	@Value("${ota.execute.duration.stophour}")
	private int	stopHour;
	
	@Value("${ota.execute.intervalminutes}")
	private int	intervalminutes;	
	
	@Autowired
	AimsRepository	aimsRepository;
	
	@Autowired
	OTAScheduleRepository	otaScheduleRepository;
	
	@Autowired
	FirmwarePackageRepository	fwPackageRepositoty;
	
	/*
	public List<LocalDateTime> setAutoArrangeOTASchedule(int gwCount) {
		
		List<LocalDateTime> scheduleList = new ArrayList<LocalDateTime>();
		
		LocalDateTime startDateTime = getStartTime();
		startDateTime = startDateTime.withMinute(0).withSecond(0);
		int	gwCountCorrection = 0;
		
		for(int i=0;i < gwCount;i++) {
			
			LocalDateTime schedule = startDateTime.plusMinutes((i-gwCountCorrection)*intervalminutes);
			
			// Check Whether scheduled time is over the stop time
			if (schedule.getHour() > stopHour || (schedule.getHour() > stopHour && schedule.getMinute() > 0)) {
				gwCountCorrection = i;
				startDateTime = startDateTime.plusDays(1).withHour(startHour).withMinute(0).withSecond(0);
				schedule = startDateTime;
			}
			
			scheduleList.add(schedule);
			
		}
		
		return scheduleList;
	}
	*/
	
	@Transactional
	public OTAStationScheduleDTO setAutoArrangeOTASchedule(String packageId, String stationCode) {
		
		Optional<FirmwarePackage> opFWPackage = fwPackageRepositoty.findById(Long.parseLong(packageId));
		
		// Check Whether there is no FW package
		if (!opFWPackage.isPresent()) {
			log.warn("No Firmware Package is registered.");
			return null;			
		}

		List<OTATargetGateway>	targetGWList = aimsRepository.getTargetGWList(stationCode);
		
		// Check Whether there is no GW.
		if (targetGWList.isEmpty()) {
			log.warn("No Gateway is founded under this station.");
			return null;
		}
		
		List<OTASchedule> scheduleList = new ArrayList<OTASchedule>();
		LocalDateTime startDateTime = getStartTime().withMinute(0).withSecond(0);
		int	gwCountCorrection = 0;
		
		for(int i=0;i < targetGWList.size();i++) {

			OTASchedule	schedule = new OTASchedule();
			
			schedule.setGwMac(targetGWList.get(i).getMacAddress());
			schedule.setGwIp(targetGWList.get(i).getIpAddress());
			schedule.setStationCode(stationCode);
			schedule.setFirmwarePackage(opFWPackage.get());		
			schedule.setEnabled(true);
			
			LocalDateTime otaTime = startDateTime.plusMinutes((i-gwCountCorrection)*intervalminutes);
			
			// Check Whether scheduled time is over the stop time
			if (otaTime.getHour() > stopHour || (otaTime.getHour() > stopHour && otaTime.getMinute() > 0)) {
				gwCountCorrection = i;
				startDateTime = startDateTime.plusDays(1).withHour(startHour).withMinute(0).withSecond(0);
				otaTime = startDateTime;
			}
			schedule.setOtaTime(otaTime);

			log.debug("Detail Schedule : {}", schedule.toString());
			scheduleList.add(schedule);
		}
		otaScheduleRepository.saveAll(scheduleList);
		
		// TODO : Add name property
		OTAStationScheduleDTO	ret = OTAStationScheduleDTO.builder()
										.code(stationCode)
										.gwCount(targetGWList.size())
										.first(scheduleList.get(0).getOtaTime())
										.last(scheduleList.get(scheduleList.size()-1).getOtaTime())
										.build();
		

		
		return ret;
	}
	
	private LocalDateTime getStartTime() {

		LocalDateTime	startLdt = LocalDateTime.now();
		
		// If scheduled time is over one hour, start date should be today. 
		if (startLdt.getHour() < (startHour-1)) return startLdt;
		
		startLdt.plusDays(1L);
		
		return startLdt;
	}
}

package com.solum.fwmanager.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.entity.OTASchedule;
import com.solum.fwmanager.repository.OTAScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleService {
	
	@Autowired
	OTAScheduleRepository	otaScheduleRepository;
	
	public LocalDateTime	getOTAScheduleByMac(String mac) {
		
		Optional<OTASchedule>	opOTASchedule = otaScheduleRepository.findById(mac);
		
		if (opOTASchedule.isPresent()) {
			
			LocalDateTime	currentTime = LocalDateTime.now();
			
			if (opOTASchedule.get().getOtaTime().isAfter(currentTime.minusMinutes(5))
				&& (opOTASchedule.get().getOtaTime().isBefore(currentTime.plusMinutes(5)) 
					|| opOTASchedule.get().getOtaTime().isEqual(currentTime.plusMinutes(5)))) {
				return opOTASchedule.get().getOtaTime();
			}
			
		}
		
		return LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);
	}
}

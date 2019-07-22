package com.solum.fwmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solum.fwmanager.entity.OTASchedule;

public interface OTAScheduleRepository extends JpaRepository<OTASchedule, String> {

}

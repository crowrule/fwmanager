package com.solum.fwmanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter @Setter
public class OTASchedule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String gwMac;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firmwarePackage_id")
    private FirmwarePackage firmwarePackage;
	
	private String	stationCode;
	
	private String	gwIp;
	
	private LocalDateTime	otaTime;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime	createdTime;
	
	@UpdateTimestamp
	private LocalDateTime	updatedTime;	

	@Transient
	public	String toString() {
		return new StringBuilder("Station : ").append(stationCode)
				.append(", GW Mac : ").append(gwMac)
				.append(", GW IP : ").append(gwIp)
				.append(", OTA Time : ").append(otaTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.toString();
	}
}

package com.solum.fwmanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter @Setter
public class FirmwarePackage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;	
	
	private String	type;
	
	private String	attribute;
	
	private	String	siteCode;
	
	private	int	jobNumber;
	
	private	String	fwVersion;
	
	private short	tagClass;
	
	private short	mode;
	
	private	String	fileName;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime	createdTime;
	
	@UpdateTimestamp
	private LocalDateTime	updatedTime;
	
	@Builder
	public FirmwarePackage(String type, String attribute, String siteCode, int jobNumber, String fwVersion, short tagClass, short mode, String	fileName) {
		this.type = type;
		this.attribute = attribute;
		this.siteCode = siteCode;
		this.jobNumber = jobNumber;
		this.fwVersion = fwVersion;
		this.tagClass = tagClass;
		this.mode = mode;
		this.fileName = fileName;
	}	
}

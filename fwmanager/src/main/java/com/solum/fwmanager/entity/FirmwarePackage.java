package com.solum.fwmanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	
	private String	tagTypeCode;
	
	private	int	jobNumber;
	
	private	int	fwVersion;
	
	private int	compType;
	
	private int	compSize;
	
	private int decompSize;
	
	private int	tagClass;
	
	private int	otaMode;
	
	private	String	fileName;
	
	private String	tagType;
	
	// TODO : Add Checksum property
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime	createdTime;
	
	@UpdateTimestamp
	private LocalDateTime	updatedTime;
	
	@Builder
	public FirmwarePackage(String tagTypeCode, int jobNumber, int fwVersion, short compType, int compSize, int decompSize, short tagClass, short otaMode, String fileName,String tagType) {
		this.tagTypeCode = tagTypeCode;
		this.jobNumber = jobNumber;
		this.fwVersion = fwVersion;
		this.compType = compType;
		this.compSize = compSize;
		this.decompSize = decompSize;
		this.tagClass = tagClass;
		this.otaMode = otaMode;
		this.fileName = fileName;
		this.tagType = tagType;
	}	
}

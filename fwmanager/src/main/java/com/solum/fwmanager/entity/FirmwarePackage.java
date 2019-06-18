package com.solum.fwmanager.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private UUID	id;	
	
	private String	type;
	
	private String	attribute;
	
	private	String	siteCode3;
	
	@Column(name="jobnum")
	private	int	jobNumber;
	
	private	String	fwVersion;
	
	private short	tagClass;
	
	private short	mode;
	
	private	String	file_Name;
	
	@Builder
	public FirmwarePackage(String type, String attribute, String siteCode3, int jobNumber, String fwVersion, short tagClass, short mode, String	file_Name) {
		this.type = type;
		this.attribute = attribute;
		this.siteCode3 = siteCode3;
		this.jobNumber = jobNumber;
		this.fwVersion = fwVersion;
		this.tagClass = tagClass;
		this.mode = mode;
		this.file_Name = file_Name;
	}	
}

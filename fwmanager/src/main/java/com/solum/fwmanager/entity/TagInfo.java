package com.solum.fwmanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
//@Entity
//@Getter @Setter
public class TagInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private	String	type;
	
	private String	name;
	
	private	int	width;
	
	private int height;
	
	private String	compressionType;
	
	private int	rotate;
	
	// For 12 Digits
	private	String	attribute;
	
	// For 16, 17 Digits
	private String	oldAttribute;
	
	private	String	chipset;
	
	// 7Seg, Mono Image, Red Color, Yellow Color 
	private String	tagClass;
	
	private boolean	flip;
	
	private boolean	nfc;

/*
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime	createdTime;
	
	@UpdateTimestamp
	private LocalDateTime	updatedTime;
*/
}

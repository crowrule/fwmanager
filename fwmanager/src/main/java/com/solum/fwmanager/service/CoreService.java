package com.solum.fwmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solum.fwmanager.external.CoreDao;

@Service
public class CoreService {

	@Autowired
	CoreDao	coreDao;
	
	public	long getGwCount() {
		return coreDao.getTest();
	}
}

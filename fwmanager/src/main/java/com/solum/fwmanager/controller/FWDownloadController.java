package com.solum.fwmanager.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class FWDownloadController {

	@RequestMapping(value = "checkfw", method = RequestMethod.GET)
	public String manualReservation(
			@RequestParam(required=false, defaultValue="XYZ") String stationCode
			, @RequestParam(required=false) String gwIP) throws Exception {
		
		if ("ABC".equalsIgnoreCase(stationCode)) 
		{
			return new StringBuilder("{ \n\tisFWexist : false\n")
						.append("}")
						.toString();
		}else {
			return new StringBuilder("{ \n\tisFWexist : true,\n")
					.append("\tReserved Time : 2016-06-01T01:00:00,\n")
					.append("\tPackage : ....\n")
					.append("}")
					.toString();			
		}
	}	
}

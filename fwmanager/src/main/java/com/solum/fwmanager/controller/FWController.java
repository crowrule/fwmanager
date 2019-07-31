package com.solum.fwmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.solum.fwmanager.dto.TagTypeInfoDTO;
import com.solum.fwmanager.service.CoreService;

@Controller
@RequestMapping
public class FWController {

	@Autowired
	CoreService	coreService;	
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String manualReservation(Model model) throws Exception {

		List<TagTypeInfoDTO> tagTypeList =  coreService.getTagTypeList("");
		
		model.addAttribute("tagTypes", tagTypeList);
		
		return "upload";
	}
}

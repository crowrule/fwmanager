package com.solum.fwmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.solum.fwmanager.service.CoreService;
import com.solum.fwmanager.service.FakeService;

@Controller
@RequestMapping(value="/")
public class TargetController {

	@Autowired
	FakeService	gwService;
	
	@RequestMapping(value = "targetList", method = RequestMethod.GET)
	public String targetList(Model model,
			@RequestParam(required=false, defaultValue="1") int type) throws Exception {
			
		if (type == 2 ) model.addAttribute("list", gwService.getTargetGWList2());
		else if (type == 3 ) model.addAttribute("list", gwService.getTargetGWList3());
		else if (type == 4 ) model.addAttribute("list", gwService.getTargetGWList4());
		else model.addAttribute("list", gwService.getTargetGWList());
			
		return "targetList";
	}
	
	@RequestMapping(value = "auto", method = RequestMethod.GET)
	public String autoReservation(Model model) throws Exception {
		return "auto";
	}
	
	@RequestMapping(value = "manual", method = RequestMethod.GET)
	public String manualReservation(Model model) throws Exception {
		return "manual";
	}
}

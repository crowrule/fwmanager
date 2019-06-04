package com.solum.fwmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class FWController {

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String manualReservation(Model model) throws Exception {
		return "upload";
	}
}

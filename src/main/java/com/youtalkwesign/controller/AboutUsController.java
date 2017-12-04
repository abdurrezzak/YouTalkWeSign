package com.youtalkwesign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutUsController {

	@RequestMapping(value = "/about-us", method = RequestMethod.GET)
	public String getInfo() {
		return "about-us-fragment :: result";		
	}
}

package com.youtalkwesign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtalkwesign.model.Text;
import com.youtalkwesign.model.Word;
import com.youtalkwesign.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayWelcomePage() {
		return "welcome";
	}		
		
	@RequestMapping(value = "/watch", method = RequestMethod.GET)
	public String displayPageWithVideo(@RequestParam String v, ModelMap model) {
		// get transcript
		List<Text> transcript = service.getTranscript(v);
		
		// get words
		List<Word> words = service.getWords(transcript);

		model.addAttribute("v", v);
		model.addAttribute("transcript", transcript);
		model.addAttribute("words", words);
		return "welcome";
	}
}

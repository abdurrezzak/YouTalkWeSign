package com.youtalkwesign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtalkwesign.model.Text;
import com.youtalkwesign.model.Word;
import com.youtalkwesign.repository.WordRepository;
import com.youtalkwesign.service.HeartedService;
import com.youtalkwesign.service.HistoryService;
import com.youtalkwesign.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService service;
	
	@Autowired
	WordRepository wordRepo;
	
	@Autowired
	HeartedService heartedService;
	
	@Autowired
	HistoryService historyService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayWelcomePage() {
		return "welcome";
	}		
		
	@RequestMapping(value = "/watch", method = RequestMethod.GET)
	public String displayPageWithVideo(@RequestParam String v, ModelMap model) {
		// put video id to model
		model.addAttribute("v", v);
		
		// get transcript
		List<Text> transcript = service.getTranscript(v);
		
		if (transcript.size() == 0) {
			String thumbnailImageUrl = ("https://i.ytimg.com/vi/" + v + "/maxresdefault.jpg");
			model.addAttribute("thumbnailImageUrl", thumbnailImageUrl);
			model.addAttribute("hasHeart", false);
			return "welcome-no-subtitles";
		}
		
		// get words
		List<Word> words = service.getWords(transcript);
		
		model.addAttribute("transcript", transcript);
		model.addAttribute("words", words);
		
		// put username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", username);
		
		if (!username.equals("anonymousUser")) {
			// add video to user's history
			historyService.addVideoToHistory(username, v);
		}
		
		// check if user has heart on this video
		model.addAttribute("hasHeart", heartedService.hasHeart(username, v));
		return "welcome";
	}
}

package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;
import com.youtalkwesign.service.HistoryService;

@Controller
public class HistoryController {
	
	@Autowired
	HistoryService service;

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String getHistoryVideos(ModelMap model) {
		
		// get username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Video> historyVideos = new ArrayList<Video>();
		if (username.equals("anonymousUser")) {
			model.addAttribute("historyVideos", historyVideos);
		} else {
			historyVideos = service.getHistoryVideos(username);
			model.addAttribute("historyVideos", historyVideos);
		}

		return "history-fragment :: result";
	}
}

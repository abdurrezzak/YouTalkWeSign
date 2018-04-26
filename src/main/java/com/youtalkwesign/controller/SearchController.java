package com.youtalkwesign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtalkwesign.model.Video;
import com.youtalkwesign.service.SearchService;


@Controller
public class SearchController {
	
	@Autowired
	SearchService service;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getLink(@RequestParam String keyword, ModelMap model) {
		
		List<Video> searchVideos = service.getResults(keyword);

		model.addAttribute("searchVideos", searchVideos);
		
		return "search-fragment :: result";			
	}

}

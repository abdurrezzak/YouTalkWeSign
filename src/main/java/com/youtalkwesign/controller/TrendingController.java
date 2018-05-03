package com.youtalkwesign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;
import com.youtalkwesign.service.TrendingService;

@Controller
public class TrendingController {
	
	@Autowired
	TrendingService service;
	
	@RequestMapping(value = "/trending", method = RequestMethod.GET)
	public String getHeartedVideos(ModelMap model) {
		List<Video> trendingVideos = service.getTrendingVideos();

		model.addAttribute("trendingVideos", trendingVideos);
		
		return "trending-fragment :: result";
	}
}

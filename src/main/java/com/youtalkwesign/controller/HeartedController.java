package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtalkwesign.model.Video;
import com.youtalkwesign.service.HeartedService;

@Controller
public class HeartedController {
	
	@Autowired
	HeartedService service;

	@RequestMapping(value = "/hearted", method = RequestMethod.GET)
	public String getHeartedVideos(ModelMap model) {
		
		// get username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Video> heartedVideos = new ArrayList<Video>();
		if (username.equals("anonymousUser")) {
			model.addAttribute("heartedVideos", heartedVideos);
		} else {
			heartedVideos = service.getHeartedVideos(username);
			model.addAttribute("heartedVideos", heartedVideos);		
		}
		
		return "hearted-fragment :: result";		
	}
	
	@RequestMapping(value = "/unheart", method = RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public String unHeart(@RequestParam String username, @RequestParam String videoId) {
		service.unheart(username, videoId);
		
		return "success";
	}
}

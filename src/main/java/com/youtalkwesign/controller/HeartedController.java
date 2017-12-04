package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;

@Controller
public class HeartedController {

	@RequestMapping(value = "/hearted", method = RequestMethod.GET)
	public String getHeartedVideos(ModelMap model) {
		List<Video> heartedVideos = new ArrayList<Video>();

		Video videoOne = new Video();
		videoOne.setId("nV50lmpVk1E");
		videoOne.setTitle("Angus & Julia Stone - A Heartbreak");
		videoOne.setThumbnailImageUrl("https://i.ytimg.com/vi/nV50lmpVk1E/maxresdefault.jpg");
		
		Video videoTwo = new Video();
		videoTwo.setId("yFTvbcNhEgc");
		videoTwo.setTitle("Angus and Julia Stone - Big Jet Plane [Official Music Video]");
		videoTwo.setThumbnailImageUrl("https://i.ytimg.com/vi/yFTvbcNhEgc/maxresdefault.jpg");
		
		Video videoThree = new Video();
		videoThree.setId("LFwcHebGZU0");
		videoThree.setTitle("Angus & Julia Stone - Snow");
		videoThree.setThumbnailImageUrl("https://i.ytimg.com/vi/LFwcHebGZU0/maxresdefault.jpg");
		
		heartedVideos.add(videoOne);
		heartedVideos.add(videoTwo);
		heartedVideos.add(videoThree);
		
		model.addAttribute("heartedVideos", heartedVideos);
		
		return "hearted-fragment :: result";		
	}
}

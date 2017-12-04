package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;

@Controller
public class TrendingController {
	
	@RequestMapping(value = "/trending", method = RequestMethod.GET)
	public String getHeartedVideos(ModelMap model) {
		List<Video> trendingVideos = new ArrayList<Video>();

		Video videoOne = new Video();
		videoOne.setId("_QZpK2OT1Ws");
		videoOne.setTitle("GoT - Battle of the Bastards - Jon Snow y Rickon Stark");
		videoOne.setThumbnailImageUrl("https://i.ytimg.com/vi/_QZpK2OT1Ws/maxresdefault.jpg");	
		
		Video videoTwo = new Video();
		videoTwo.setId("k-NxgYt56pM");
		videoTwo.setTitle("Trump's approval rating hits historic low, Washington Post-ABC poll says");
		videoTwo.setThumbnailImageUrl("https://i.ytimg.com/vi/k-NxgYt56pM/maxresdefault.jpg");
				
		Video videoThree = new Video();
		videoThree.setId("ekvA17eyfdg");
		videoThree.setTitle("Why Edward Norton Doesn't Get Many Movie Offers");
		videoThree.setThumbnailImageUrl("https://i.ytimg.com/vi/ekvA17eyfdg/maxresdefault.jpg");
		
		Video videoFour = new Video();
		videoFour.setId("cKxRvEZd3Mw");
		videoFour.setTitle("Hello World - Machine Learning Recipes #1");
		videoFour.setThumbnailImageUrl("https://i.ytimg.com/vi/cKxRvEZd3Mw/maxresdefault.jpg");
		
		Video videoFive = new Video();
		videoFive.setId("F463aM90ZPM");
		videoFive.setTitle("Top 10 Best Prison Break Moments");
		videoFive.setThumbnailImageUrl("https://i.ytimg.com/vi/F463aM90ZPM/maxresdefault.jpg");
	
		trendingVideos.add(videoOne);
		trendingVideos.add(videoTwo);
		trendingVideos.add(videoThree);
		trendingVideos.add(videoFour);
		trendingVideos.add(videoFive);
		model.addAttribute("trendingVideos", trendingVideos);
		
		return "trending-fragment :: result";
	}
}
